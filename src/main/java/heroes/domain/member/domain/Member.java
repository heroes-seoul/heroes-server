package heroes.domain.member.domain;

import static heroes.domain.member.domain.MemberRole.COMPANY;
import static heroes.domain.member.domain.MemberRole.USER;
import static heroes.domain.member.domain.MemberStatus.NORMAL;

import heroes.domain.common.model.BaseTimeEntity;
import heroes.domain.company.domain.Company;
import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    private Member(
            String username,
            String nickname,
            District district,
            OauthInfo oauthInfo,
            MemberRole role,
            MemberStatus status,
            Company company) {
        this.username = username;
        this.nickname = nickname;
        this.district = district;
        this.oauthInfo = oauthInfo;
        this.role = role;
        this.status = status;
        this.company = company;
    }

    public static Member createNormalMember(OauthInfo oauthInfo, String username) {
        return Member.builder()
                .username(username)
                .nickname(null)
                .district(null)
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
}
