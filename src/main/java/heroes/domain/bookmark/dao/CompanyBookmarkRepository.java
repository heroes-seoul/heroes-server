package heroes.domain.bookmark.dao;

import heroes.domain.bookmark.domain.CompanyBookmark;
import heroes.domain.company.domain.Company;
import heroes.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyBookmarkRepository extends JpaRepository<CompanyBookmark, Long> {
    boolean existsByCompanyAndMember(Company company, Member member);

    Optional<CompanyBookmark> findByCompanyAndMember(Company company, Member member);
}
