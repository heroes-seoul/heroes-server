package heroes.domain.company.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class CompanyImageUrl {
    private String mainImageUrl;
    private String firstSubImageUrl;
    private String secondSubImageUrl;
}
