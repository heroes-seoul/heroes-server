package heroes.domain.company.application;

import heroes.domain.common.presignedurl.application.PresignedUrlService;
import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.company.dao.CompanyRepository;
import heroes.domain.company.domain.Company;
import heroes.domain.company.dto.request.ImageType;
import heroes.global.common.validations.EnumValue;
import heroes.global.util.CompanyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static heroes.global.common.constants.PresignedUrlConstants.*;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final PresignedUrlService presignedUrlService;
    private final CompanyRepository companyRepository;
    private final CompanyUtil companyUtil;

    public List<PresignedUrlIssueResponse> getImageUploadUrl(
            @EnumValue(enumClass = ImageType.class, message = "결제 유형이 잘못되었습니다.", ignoreCase = true)
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
            default -> throw new IllegalArgumentException("Invalid imageType: " + imageType);
        };
    }


}
