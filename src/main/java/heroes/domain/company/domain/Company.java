package heroes.domain.company.domain;

import heroes.domain.atmosphere.domain.CompanyAtmosphere;
import heroes.domain.bookmark.domain.CompanyBookmark;
import heroes.domain.company.dto.request.CompanyCreateRequest;
import heroes.domain.company.dto.request.CompanyUpdateRequest;
import heroes.domain.companyhour.domain.CompanyHour;
import heroes.domain.review.domain.CompanyReview;
import heroes.domain.sublevel.domain.CompanySubLevel;
import heroes.domain.type.domain.CompanyType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

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

    private String address;

    private String addressDetail;

    @Column(length = 100)
    private String phoneNumber;

    private String companyDescription;

    private String companyUrl;

    @Embedded private CompanyImageUrl companyImageUrl;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyBookmark> bookmarks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyHour> hours = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyReview> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanySubLevel> subLevels = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyType> typeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyAtmosphere> atmosphereList = new ArrayList<>();

    public static Company createEmptyCompany() {
        return Company.builder().build();
    }

    public void createCompany(CompanyCreateRequest request) {
        this.companyName = request.getCompanyName();
        this.address = request.getAddress();
        this.addressDetail = request.getAddressDetail();
        this.phoneNumber = request.getPhoneNumber();
        this.companyDescription = request.getCompanyDescription();
        this.companyUrl = request.getCompanyUrl();
        this.companyImageUrl =
                CompanyImageUrl.createCompanyImageUrl(
                        request.getCompanyMainImageUrl(),
                        request.getCompanySubImageUrlList(),
                        request.getCompanyMenuImageUrl());

        request.getTypeList()
                .forEach(type -> this.typeList.add(CompanyType.createType(this, type)));
        request.getAtmosphereNameList()
                .forEach(
                        atmosphere ->
                                this.atmosphereList.add(
                                        CompanyAtmosphere.createAtmosphere(this, atmosphere)));
    }

    public void updateCompany(CompanyUpdateRequest request) {
        this.address = request.getAddress();
        this.addressDetail = request.getAddressDetail();
        this.phoneNumber = request.getPhoneNumber();
        this.companyDescription = request.getCompanyDescription();
        this.companyUrl = request.getCompanyUrl();
        this.companyImageUrl =
                CompanyImageUrl.createCompanyImageUrl(
                        request.getCompanyMainImageUrl(),
                        request.getCompanySubImageUrlList(),
                        request.getCompanyMenuImageUrl());

        this.typeList.clear();
        request.getTypeList()
                .forEach(type -> this.typeList.add(CompanyType.createType(this, type)));

        this.atmosphereList.clear();
        request.getAtmosphereNameList()
                .forEach(
                        atmosphere ->
                                this.atmosphereList.add(
                                        CompanyAtmosphere.createAtmosphere(this, atmosphere)));
    }

    public void updateCompanyFinalLevel(int finalLevel) {
        this.finalLevel = finalLevel;
    }
}
