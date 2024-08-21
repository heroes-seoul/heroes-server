package heroes.domain.sublevel.application;

import static heroes.global.common.constants.PresignedUrlConstants.*;

import heroes.domain.common.presignedurl.application.PresignedUrlService;
import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.company.domain.Company;
import heroes.domain.sublevel.dao.CompanySubLevelRepository;
import heroes.domain.sublevel.domain.CompanySubLevel;
import heroes.domain.sublevel.domain.SubLevel;
import heroes.domain.sublevel.dto.request.SubLevelUpdateRequest;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.global.util.CompanyUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubLevelService {
    private final PresignedUrlService presignedUrlService;
    private final CompanyUtil companyUtil;
    private final CompanySubLevelRepository companySubLevelRepository;

    public List<PresignedUrlIssueResponse> getSubLevelImageUrl(SubLevel subLevel, int count) {
        final Company currentCompany = companyUtil.getCurrentCompany();
        return presignedUrlService.generatePresignedUrls(
                COMPANY_VERIFY_DIRECTORY + currentCompany.getId() + SLASH + subLevel.toString(),
                count);
    }

    public void updateCompanySubLevel(SubLevelUpdateRequest request) {
        final Company currentCompany = companyUtil.getCurrentCompany();

        validateCompanySubLevel(request, currentCompany);

        List<CompanySubLevel> companySubLevels =
                request.getUploadedImageUrlList().stream()
                        .map(
                                url ->
                                        CompanySubLevel.create(
                                                request.getSubLevel(),
                                                currentCompany,
                                                presignedUrlService.convertToCloudfrontUrl(url)))
                        .collect(Collectors.toList());

        companySubLevelRepository.saveAll(companySubLevels);
        currentCompany.updateCompanyFinalLevel(request.getSubLevel().getLevel());
    }

    private static void validateCompanySubLevel(
            SubLevelUpdateRequest request, Company currentCompany) {
        if (currentCompany.getFinalLevel() + 1 < request.getSubLevel().getLevel()) {
            throw new CustomException(ErrorCode.NOT_ALLOWED_SUBLEVEL);
        }
    }
}
