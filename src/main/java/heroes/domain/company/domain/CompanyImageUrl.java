package heroes.domain.company.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
public class CompanyImageUrl {
    private String mainImageUrl;
    private String firstSubImageUrl;
    private String secondSubImageUrl;
    private String menuImageUrl;

    @Builder
    public CompanyImageUrl(String mainImageUrl, String firstSubImageUrl, String secondSubImageUrl, String menuImageUrl) {
        this.mainImageUrl = mainImageUrl;
        this.firstSubImageUrl = firstSubImageUrl;
        this.secondSubImageUrl = secondSubImageUrl;
        this.menuImageUrl = menuImageUrl;
    }

    public static CompanyImageUrl createCompanyImageUrl(String mainImageUrl, List<String> companySubImageUrlList, String menuImageUrl) {
        return CompanyImageUrl.builder()
                .mainImageUrl(mainImageUrl)
                .firstSubImageUrl(companySubImageUrlList.get(0))
                .secondSubImageUrl(companySubImageUrlList.get(1))
                .menuImageUrl(menuImageUrl)
                .build();
    }
}
