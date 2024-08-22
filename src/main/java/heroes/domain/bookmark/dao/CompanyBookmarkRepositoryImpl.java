package heroes.domain.bookmark.dao;

import static heroes.domain.bookmark.domain.QCompanyBookmark.companyBookmark;
import static heroes.domain.company.domain.QCompany.company;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import heroes.domain.company.domain.Company;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class CompanyBookmarkRepositoryImpl implements CompanyBookmarkRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Company> findAllCompanyBookmarked(
            Long memberId, Long lastCompanyId, int pageSize) {
        List<Company> results =
                jpaQueryFactory
                        .select(company)
                        .from(companyBookmark)
                        .join(companyBookmark.company, company)
                        .where(companyBookmark.member.id.eq(memberId), lastCompanyId(lastCompanyId))
                        .orderBy(company.id.desc())
                        .limit(pageSize + 1)
                        .fetch();

        if (results.isEmpty()) {
            throw new CustomException(ErrorCode.BOOKMARKED_COMPANY_NOT_FOUND);
        }

        return checkLastPage(pageSize, results);
    }

    private BooleanExpression lastCompanyId(Long lastCompanyId) {
        if (lastCompanyId == null) {
            return null;
        }

        return company.id.lt(lastCompanyId);
    }

    private Slice<Company> checkLastPage(int pageSize, List<Company> results) {
        boolean hasNext = results.size() > pageSize;
        if (hasNext) {
            results.remove(pageSize);
        }
        return new SliceImpl<>(results, PageRequest.of(0, pageSize), hasNext);
    }
}
