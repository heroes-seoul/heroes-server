package heroes.domain.company.dao;

import static heroes.domain.atmosphere.domain.QCompanyAtmosphere.companyAtmosphere;
import static heroes.domain.type.domain.QCompanyType.companyType;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import heroes.domain.atmosphere.domain.Atmosphere;
import heroes.domain.company.domain.Company;
import heroes.domain.company.domain.QCompany;
import heroes.domain.type.domain.Type;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    @Override
    public Slice<Company> searchCompanies(
            String companyName,
            List<Type> types,
            List<Atmosphere> atmospheres,
            int pageSize,
            Long lastCompanyId) {
        List<Company> results =
                jpaQueryFactory
                        .selectFrom(company)
                        .leftJoin(company.typeList, companyType)
                        .leftJoin(company.atmosphereList, companyAtmosphere)
                        .where(
                                companyNameLike(companyName),
                                hasAllCompanyTypes(types),
                                hasAllCompanyAtmospheres(atmospheres),
                                lastCompanyId(lastCompanyId))
                        .orderBy(company.id.desc())
                        .limit(pageSize + 1)
                        .fetch();

        if (results.isEmpty()) {
            throw new CustomException(ErrorCode.COMPANY_NOT_FOUND);
        }

        return checkLastPage(pageSize, results);
    }

    private BooleanExpression companyNameLike(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            return null;
        }
        return company.companyName.like("%" + companyName + "%");
    }

    private BooleanExpression hasAllCompanyTypes(List<Type> types) {
        if (types == null || types.isEmpty()) {
            return null;
        }

        return JPAExpressions.selectFrom(companyType)
                .where(companyType.company.eq(company).and(companyType.type.in(types)))
                .groupBy(companyType.company)
                .having(companyType.count().eq((long) types.size()))
                .exists();
    }

    private BooleanExpression hasAllCompanyAtmospheres(List<Atmosphere> atmospheres) {
        if (atmospheres == null || atmospheres.isEmpty()) {
            return null;
        }

        return JPAExpressions.selectFrom(companyAtmosphere)
                .where(
                        companyAtmosphere
                                .company
                                .eq(company)
                                .and(companyAtmosphere.atmosphere.in(atmospheres)))
                .groupBy(companyAtmosphere.company)
                .having(companyAtmosphere.count().eq((long) atmospheres.size()))
                .exists();
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
