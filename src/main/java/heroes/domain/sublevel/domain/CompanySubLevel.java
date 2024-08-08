package heroes.domain.sublevel.domain;

import heroes.domain.common.model.BaseTimeEntity;
import heroes.domain.company.domain.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanySubLevel extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_sub_level_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubLevel subLevelName;

    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
