package heroes.domain.company.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import heroes.domain.company.domain.Company;
import heroes.domain.company.domain.QCompany;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryCustomImpl implements CompanyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    QCompany company = QCompany.company;

    @Override
    public List<Company> findAllPage(Pageable pageable) {

        return jpaQueryFactory
                .selectFrom(company)
                .orderBy(company.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }
}
