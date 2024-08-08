package heroes.domain.company.domain;

import heroes.domain.member.domain.District;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(length = 100)
    private String companyName;

    private int finalLevel;

    @Enumerated(value = EnumType.STRING)
    private District district;

    @Column(length = 255)
    private String address;

    @Column(length = 255)
    private String addressDetail;

    @Column(length = 255)
    private String companyDescription;

    @Column(length = 100)
    private String phoneNumber;

    private String mapUrl;
    private String mainImageUrl;
}
