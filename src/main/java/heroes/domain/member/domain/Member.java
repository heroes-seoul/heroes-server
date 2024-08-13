package heroes.domain.member.domain;

import static heroes.domain.member.domain.MemberRole.COMPANY;
import static heroes.domain.member.domain.MemberRole.USER;
import static heroes.domain.member.domain.MemberStatus.NORMAL;

import heroes.domain.bookmark.domain.CompanyBookmark;
import heroes.domain.common.model.BaseTimeEntity;
import heroes.domain.company.domain.Company;
import heroes.domain.member.dto.request.MemberUpdateRequest;
import heroes.domain.review.domain.CompanyReview;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private District district;

    @Embedded private OauthInfo oauthInfo;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus status;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    private String profileUrl;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyBookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyReview> reviews = new ArrayList<>();

    @Builder
    private Member(
            String username,
            String nickname,
            District district,
            OauthInfo oauthInfo,
            MemberRole role,
            MemberStatus status,
            String profileUrl,
            Company company) {
        this.username = username;
        this.nickname = nickname;
        this.district = district;
        this.oauthInfo = oauthInfo;
        this.role = role;
        this.status = status;
        this.profileUrl = profileUrl;
        this.company = company;
    }

    public static Member createNormalMember(OauthInfo oauthInfo, String username) {
        return Member.builder()
                .username(username)
                .role(USER)
                .status(NORMAL)
                .oauthInfo(oauthInfo)
                .build();
    }

    public static Member createCompanyMember(
            OauthInfo oauthInfo, String username, Company company) {
        return Member.builder()
                .username(username)
                .role(COMPANY)
                .status(NORMAL)
                .oauthInfo(oauthInfo)
                .company(company)
                .build();
    }

    public void updateMemberInfo(MemberUpdateRequest request, String profileUrl) {
        this.nickname = request.getNickname();
        this.district = request.getDistrict();
        this.profileUrl = profileUrl;
    }
}
