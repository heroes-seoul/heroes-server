package heroes.domain.bookmark.domain;

import heroes.domain.company.domain.Company;
import heroes.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private CompanyBookmark(Company company, Member member) {
        this.company = company;
        this.member = member;
    }

    public static CompanyBookmark create(Company company, Member member) {
        CompanyBookmark bookmark =
                CompanyBookmark.builder().company(company).member(member).build();
        member.getBookmarks().add(bookmark);
        company.getBookmarks().add(bookmark);
        return bookmark;
    }
}
