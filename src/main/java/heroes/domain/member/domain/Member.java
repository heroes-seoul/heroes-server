package heroes.domain.member.domain;

import heroes.domain.common.model.BaseTimeEntity;
import heroes.domain.company.domain.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
}
