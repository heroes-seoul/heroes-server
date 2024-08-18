package heroes.domain.atmosphere.domain;

import heroes.domain.company.domain.Company;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyAtmosphere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private Atmosphere atmosphere;

    @Builder
    private CompanyAtmosphere(Company company, Atmosphere atmosphere) {
        this.company = company;
        this.atmosphere = atmosphere;
    }

    public static CompanyAtmosphere createAtmosphere(Company company, Atmosphere atmosphere) {
        return CompanyAtmosphere.builder().company(company).atmosphere(atmosphere).build();
    }
}
