package heroes.domain.type.domain;

import heroes.domain.company.domain.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Builder
    private CompanyType(Company company, Type type) {
        this.company = company;
        this.type = type;
    }

    public static CompanyType createType(Company company, Type type) {
        return CompanyType.builder().company(company).type(type).build();
    }
}
