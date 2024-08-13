package heroes.domain.company.domain;

import heroes.domain.atmosphere.domain.CompanyAtmosphere;
import heroes.domain.bookmark.domain.CompanyBookmark;
import heroes.domain.companyhour.domain.CompanyHour;
import heroes.domain.member.domain.District;
import heroes.domain.review.domain.CompanyReview;
import heroes.domain.sublevel.domain.CompanySubLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    private CompanyType companyType;

    private String address;

    private String addressDetail;

    private String companyDescription;

    @Column(length = 100)
    private String phoneNumber;

    private String mapUrl;

    @Embedded
    private CompanyImageUrl companyImageUrl;


    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyBookmark> bookmarks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyAtmosphere> atmospheres = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyHour> hours = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyReview> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanySubLevel> subLevels = new ArrayList<>();

    public static Company createEmptyCompany() {
        return Company.builder().build();
    }
}
