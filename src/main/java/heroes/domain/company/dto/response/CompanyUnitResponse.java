package heroes.domain.company.dto.response;

import heroes.domain.atmosphere.domain.CompanyAtmosphere;
import heroes.domain.company.domain.Company;
import heroes.domain.company.domain.CompanyImageUrl;
import heroes.domain.type.domain.CompanyType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUnitResponse {
    Long companyId;

    @Schema(description = "양육친화환경 단계")
    int finalLevel;

    @Schema(description = "기업명")
    String companyName;

    @Schema(description = "기업타입 리스트", defaultValue = "[\"CAFE\", \"RESTAURANT\"]")
    List<String> typeValueList;

    @Schema(description = "분위기 리스트", defaultValue = "[\"NEAT\", \"FUN\"]")
    List<String> atmosphereValueList;

    @Schema(description = "기업 이미지 리스트(메인, 서브, 메뉴, 양육인증 이미지) ")
    List<String> companyImageUrlList;

    @Schema(description = "기업소개", defaultValue = "아이가 행복하게 뛰놀수 있는 카페가 있습니다.")
    String companyDescription;

    @Schema(description = "주소", defaultValue = "서울특별시 동작구 여의대방로54길 18")
    private String address;

    @Schema(description = "유저 장소 북마크 여부", defaultValue = "false")
    private boolean isBookMarked;

    public static CompanyUnitResponse ofCompanyIsBookMark(Company company, boolean isBookMarked) {
        return CompanyUnitResponse.builder()
                .companyId(company.getId())
                .finalLevel(company.getFinalLevel())
                .companyName(company.getCompanyName())
                .atmosphereValueList(changeComAtsToAts(company.getAtmosphereList()))
                .typeValueList(changeComTypeToType(company.getTypeList()))
                .companyImageUrlList(buildCompanyImageUrl(company.getCompanyImageUrl()))
                .companyDescription(company.getCompanyDescription())
                .address(company.getAddress())
                .isBookMarked(isBookMarked)
                .build();
    }

    private static List<String> buildCompanyImageUrl(CompanyImageUrl companyImageUrl) {
        List<String> urlList = new ArrayList<>();
        // Main Image URL
        if (companyImageUrl.getMainImageUrl() != null) {
            urlList.add(companyImageUrl.getMainImageUrl());
        }

        // First Sub Image URL (null이면 추가하지 않음)
        if (companyImageUrl.getFirstSubImageUrl() != null) {
            urlList.add(companyImageUrl.getFirstSubImageUrl());
        }

        // Second Sub Image URL (null이면 추가하지 않음)
        if (companyImageUrl.getSecondSubImageUrl() != null) {
            urlList.add(companyImageUrl.getSecondSubImageUrl());
        }
        return urlList;
    }

    private static List<String> changeComAtsToAts(
            List<CompanyAtmosphere> companyAtmosphereList) {
        List<String> atmosphereValueList = new ArrayList<>();
        for (CompanyAtmosphere companyAtmosphere : companyAtmosphereList) {
            atmosphereValueList.add(companyAtmosphere.getAtmosphere().toString());
        }
        return atmosphereValueList;
    }

    private static List<String> changeComTypeToType(List<CompanyType> companyTypeList) {
        List<String> typeValueList = new ArrayList<>();
        for (CompanyType companyType : companyTypeList) {
            typeValueList.add(companyType.getType().toString());
        }
        return typeValueList;
    }
}
