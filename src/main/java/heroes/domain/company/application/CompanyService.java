package heroes.domain.company.application;

import heroes.domain.common.presignedurl.application.PresignedUrlService;
import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.company.dao.CompanyRepository;
import heroes.domain.company.domain.Company;
import heroes.domain.company.dto.request.CompanyCreateRequest;
import heroes.domain.company.dto.request.ImageType;
import heroes.domain.company.dto.response.CompanyCreateResponse;
import heroes.global.common.validations.EnumValue;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.global.util.CompanyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static heroes.global.common.constants.PresignedUrlConstants.*;
@Transactional
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final PresignedUrlService presignedUrlService;
    private final CompanyUtil companyUtil;

    public List<PresignedUrlIssueResponse> getImageUploadUrl(
            @EnumValue(enumClass = ImageType.class, message = "이미지 유형이 잘못되었습니다.", ignoreCase = true)
            String imageType,
            int size) {
        Company currentCompany = companyUtil.getCurrentCompany();

        return switch (imageType) {
            case "MAIN" -> List.of(presignedUrlService.generatePresignedUrl(
                    COMPANY_DIRECTORY + COMPANY_MAIN_DIRECTORY + currentCompany.getId().toString()));
            case "SUB" -> IntStream.range(0, size)
                    .mapToObj(i -> presignedUrlService.generatePresignedUrl(
                            COMPANY_DIRECTORY + COMPANY_SUB_DIRECTORY + currentCompany.getId().toString() + SLASH + i))
                    .collect(Collectors.toList());
            case "MENU" -> List.of(presignedUrlService.generatePresignedUrl(
                    COMPANY_DIRECTORY + COMPANY_MENU_DIRECTORY + currentCompany.getId().toString()));
            default -> throw new CustomException(ErrorCode.INVALID_IMAGETYPE);
        };
    }


    public CompanyCreateResponse updateCompany(CompanyCreateRequest request) {
        // 기업 존재 여부 확인
        Company company = companyUtil.getCurrentCompany();

        // 과거 기업의 정보를 수정한다.
        company.updateCompany(request);

        return new CompanyCreateResponse(company.getId());
    }
}
