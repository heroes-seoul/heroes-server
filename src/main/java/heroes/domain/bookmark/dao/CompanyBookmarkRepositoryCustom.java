package heroes.domain.bookmark.dao;

import heroes.domain.company.domain.Company;
import org.springframework.data.domain.Slice;

public interface CompanyBookmarkRepositoryCustom {
    Slice<Company> findAllCompanyBookmarked(Long memberId, Long lastCompanyId, int pageSize);
}
