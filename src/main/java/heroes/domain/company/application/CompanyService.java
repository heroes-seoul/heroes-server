package heroes.domain.company.application;

import heroes.domain.common.presignedurl.application.PresignedUrlService;
import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.company.domain.Company;
import heroes.domain.company.dto.request.CompanyCreateRequest;
import heroes.domain.company.dto.request.ImageType;
import heroes.domain.company.dto.response.CompanyCreateResponse;
import heroes.domain.companyhour.domain.CompanyHour;
import heroes.domain.companyhour.domain.DayOfWeek;
import heroes.domain.companyhour.dto.CompanyHourCreateRequest;
import heroes.global.common.validations.EnumValue;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.global.util.CompanyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static heroes.global.common.constants.MessageConstants.INVALID_IMAGE_TYPE_MESSAGE;
import static heroes.global.common.constants.PresignedUrlConstants.*;
@Transactional
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final PresignedUrlService presignedUrlService;
    private final CompanyUtil companyUtil;

    public List<PresignedUrlIssueResponse> getImageUploadUrl(
            @EnumValue(enumClass = ImageType.class, message = INVALID_IMAGE_TYPE_MESSAGE, ignoreCase = true)
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
        updateCompanyHours(company, request);

        return new CompanyCreateResponse(company.getId());
    }

    private List<CompanyHour> buildCompanyHourList(List<CompanyHourCreateRequest> hourRequests, Company company) {
        List<CompanyHour> hours = new ArrayList<>();
        for (CompanyHourCreateRequest hourRequest : hourRequests) {
            hours.add(
                    CompanyHour.buildCompanyHour(
                            DayOfWeek.valueOf(hourRequest.getDayOfWeek()),
                            hourRequest.getStartTime(),
                            hourRequest.getEndTime(),
                            company
                    ));
        }
        return hours;
    }
    private void setCompanyHours(Company company, List<CompanyHour> newHours) {
        company.getHours().clear();
        company.getHours().addAll(newHours);
    }

    private void updateCompanyHours(Company company, CompanyCreateRequest request) {
        List<CompanyHour> newHours = buildCompanyHourList(request.getCompanyHourCreateRequestList(), company);
        setCompanyHours(company, newHours);
    }
}
