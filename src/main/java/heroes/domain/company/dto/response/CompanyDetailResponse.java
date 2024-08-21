package heroes.domain.company.dto.response;

import heroes.domain.company.domain.Company;
import heroes.domain.companyhour.dto.CompanyHourDto;
import heroes.domain.review.dto.CompanyReviewDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
public class CompanyDetailResponse {
    @Schema(description = "기업 이름")
    private String companyName;

    @Schema(description = "기업 최종 양육단게")
    private int finalLevel;

    @Schema(description = "기업 도로명 주소")
    private String address;

    @Schema(description = "기업 상세 주소")
    private String addressDetail;

    @Schema(description = "기업 전화번호")
    private String phoneNumber;

    @Schema(description = "기업 설명")
    private String companyDescription;

    @Schema(description = "기업 관련 링크")
    private String companyUrl;

    @Schema(description = "기업 메인 이미지 url")
    private String mainImageUrl;

    @Schema(description = "기업 첫 번째 서브 이미지 url")
    private String firstSubImageUrl;

    @Schema(description = "기업 두 번째 서브 이미지 url")
    private String secondSubImageUrl;

    @Schema(description = "기업 메뉴 이미지 url")
    private String menuImageUrl;

    @Schema(description = "기업 분위기 리스트")
    private List<String> atmospheres;

    @Schema(description = "기업 영업 시간 리스트")
    private List<CompanyHourDto> hours;

    @Schema(description = "기업 후기 댓글 리스트")
    private List<CompanyReviewDto> reviews;

    @Schema(description = "기업 종류 리스트")
    private List<String> types;

    @Schema(description = "기업 양육단계 인증 이미지 url 리스트")
    private List<String> verifyImageUrls;

    @Schema(description = "해당 멤버의 북마크 여부")
    private boolean isBookmarked;

    public CompanyDetailResponse(
            List<String> atmospheres,
            List<CompanyHourDto> hours,
            List<CompanyReviewDto> reviews,
            List<String> types,
            List<String> verifyImageUrls) {
        this.atmospheres = atmospheres;
        this.hours = hours;
        this.reviews = reviews;
        this.types = types;
        this.verifyImageUrls = verifyImageUrls;
    }

    public CompanyDetailResponse setCompanyData(Company company) {
        this.companyName = company.getCompanyName();
        this.finalLevel = company.getFinalLevel();
        this.address = company.getAddress();
        this.addressDetail = company.getAddressDetail();
        this.phoneNumber = company.getPhoneNumber();
        this.companyDescription = company.getCompanyDescription();
        this.companyUrl = company.getCompanyUrl();
        this.mainImageUrl = company.getCompanyImageUrl().getMainImageUrl();
        this.firstSubImageUrl = company.getCompanyImageUrl().getFirstSubImageUrl();
        this.secondSubImageUrl = company.getCompanyImageUrl().getSecondSubImageUrl();
        this.menuImageUrl = company.getCompanyImageUrl().getMenuImageUrl();
        return this;
    }

    public CompanyDetailResponse setBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
        return this;
    }
}
