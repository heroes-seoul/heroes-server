package heroes.domain.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class CompanyImageUrl {
    @Column(length = 1000)
    private String mainImageUrl;

    @Column(length = 1000)
    private String firstSubImageUrl;

    @Column(length = 1000)
    private String secondSubImageUrl;

    @Column(length = 1000)
    private String menuImageUrl;

    @Builder
    public CompanyImageUrl(
            String mainImageUrl,
            String firstSubImageUrl,
            String secondSubImageUrl,
            String menuImageUrl) {
        this.mainImageUrl = mainImageUrl;
        this.firstSubImageUrl = firstSubImageUrl;
        this.secondSubImageUrl = secondSubImageUrl;
        this.menuImageUrl = menuImageUrl;
    }

    public static CompanyImageUrl createCompanyImageUrl(
            String mainImageUrl, List<String> companySubImageUrlList, String menuImageUrl) {
        return CompanyImageUrl.builder()
                .mainImageUrl(mainImageUrl)
                .firstSubImageUrl(companySubImageUrlList.get(0))
                .secondSubImageUrl(companySubImageUrlList.get(1))
                .menuImageUrl(menuImageUrl)
                .build();
    }
}
