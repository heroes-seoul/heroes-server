package heroes.domain.company.application;

import static heroes.global.common.constants.MessageConstants.INVALID_IMAGE_TYPE_MESSAGE;
import static heroes.global.common.constants.PresignedUrlConstants.*;

import heroes.domain.bookmark.dao.CompanyBookmarkRepository;
import heroes.domain.common.presignedurl.application.PresignedUrlService;
import heroes.domain.common.presignedurl.dto.response.PresignedUrlIssueResponse;
import heroes.domain.company.dao.CompanyRepository;
import heroes.domain.company.domain.Company;
import heroes.domain.company.dto.request.CompanyCreateRequest;
import heroes.domain.company.dto.request.CompanyUpdateRequest;
import heroes.domain.company.dto.request.ImageType;
import heroes.domain.company.dto.response.CompanyChangeResponse;
import heroes.domain.company.dto.response.CompanyUnitResponse;
import heroes.domain.companyhour.domain.CompanyHour;
import heroes.domain.companyhour.domain.DayOfWeek;
import heroes.domain.companyhour.dto.CompanyHourCreateRequest;
import heroes.domain.member.domain.Member;
import heroes.global.common.validations.EnumValue;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.global.util.CompanyUtil;
import heroes.global.util.MemberUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final PresignedUrlService presignedUrlService;
    private final CompanyUtil companyUtil;
    private final CompanyRepository companyRepository;
    private final MemberUtil memberUtil;
    private final CompanyBookmarkRepository companyBookmarkRepository;

    public List<PresignedUrlIssueResponse> getImageUploadUrl(
            @EnumValue(
                            enumClass = ImageType.class,
                            message = INVALID_IMAGE_TYPE_MESSAGE,
                            ignoreCase = true)
                    String imageType,
            int size) {
        Company currentCompany = companyUtil.getCurrentCompany();

        return switch (imageType) {
            case "MAIN" -> List.of(
                    presignedUrlService.generatePresignedUrl(
                            COMPANY_DIRECTORY
                                    + COMPANY_MAIN_DIRECTORY
                                    + currentCompany.getId().toString()));
            case "SUB" -> IntStream.range(0, size)
                    .mapToObj(
                            i ->
                                    presignedUrlService.generatePresignedUrl(
                                            COMPANY_DIRECTORY
                                                    + COMPANY_SUB_DIRECTORY
                                                    + currentCompany.getId().toString()
                                                    + SLASH
                                                    + i))
                    .collect(Collectors.toList());
            case "MENU" -> List.of(
                    presignedUrlService.generatePresignedUrl(
                            COMPANY_DIRECTORY
                                    + COMPANY_MENU_DIRECTORY
                                    + currentCompany.getId().toString()));
            default -> throw new CustomException(ErrorCode.INVALID_IMAGETYPE);
        };
    }

    public CompanyChangeResponse createCompany(CompanyCreateRequest request) {
        // 기업 존재 여부 확인
        Company company = companyUtil.getCurrentCompany();

        // 과거 기업의 정보를 수정한다.
        company.createCompany(request);
        updateCompanyHours(company, request.getCompanyHourCreateRequestList());

        return new CompanyChangeResponse(company.getId());
    }

    public CompanyChangeResponse updateCompany(CompanyUpdateRequest request) {
        // 기업 존재 여부 확인
        Company company = companyUtil.getCurrentCompany();

        // 가게 이름을 제외한 기업 정보 수정 가능
        company.updateCompany(request);
        updateCompanyHours(company, request.getCompanyHourCreateRequestList());

        return new CompanyChangeResponse(company.getId());
    }

    private List<CompanyHour> buildCompanyHourList(
            List<CompanyHourCreateRequest> hourRequests, Company company) {
        List<CompanyHour> hours = new ArrayList<>();
        for (CompanyHourCreateRequest hourRequest : hourRequests) {
            hours.add(
                    CompanyHour.buildCompanyHour(
                            DayOfWeek.valueOf(hourRequest.getDayOfWeek()),
                            hourRequest.getStartTime(),
                            hourRequest.getEndTime(),
                            company));
        }
        return hours;
    }

    private void setCompanyHours(Company company, List<CompanyHour> newHours) {
        company.getHours().clear();
        company.getHours().addAll(newHours);
    }

    private void updateCompanyHours(Company company, List<CompanyHourCreateRequest> hourRequests) {
        List<CompanyHour> newHours = buildCompanyHourList(hourRequests, company);
        setCompanyHours(company, newHours);
    }

    public Slice<CompanyUnitResponse> getCompanyList(Pageable pageable) {
        // 현재 기업 찾기
        Member member = memberUtil.getCurrentMember();
        // 현재 기업 정보
        List<Company> companyList = companyRepository.findAllPage(pageable);
        List<CompanyUnitResponse> companyUnitResponseList = new ArrayList<>();
        for (Company company : companyList) {
            // 멤버 bookmark 확인
            companyUnitResponseList.add(
                    CompanyUnitResponse.ofCompanyIsBookMark(
                            company, checkIsBookMarked(company, member)));
        }

        boolean hasNext = false;
        if (companyList.size() > pageable.getPageSize()) {
            companyList.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(companyUnitResponseList, pageable, hasNext);
    }

    private boolean checkIsBookMarked(Company company, Member member) {
        return companyBookmarkRepository.existsByCompanyAndMember(company, member);
    }
}
