package heroes.domain.company.dao;

import static heroes.domain.atmosphere.domain.QCompanyAtmosphere.companyAtmosphere;
import static heroes.domain.companyhour.domain.QCompanyHour.companyHour;
import static heroes.domain.member.domain.QMember.member;
import static heroes.domain.review.domain.QCompanyReview.companyReview;
import static heroes.domain.sublevel.domain.QCompanySubLevel.companySubLevel;
import static heroes.domain.type.domain.QCompanyType.companyType;

import com.querydsl.jpa.impl.JPAQueryFactory;
import heroes.domain.company.domain.Company;
import heroes.domain.company.domain.QCompany;
import heroes.domain.company.dto.response.CompanyDetailResponse;
import heroes.domain.companyhour.dto.CompanyHourDto;
import heroes.domain.companyhour.dto.QCompanyHourDto;
import heroes.domain.review.dto.CompanyReviewDto;
import heroes.domain.review.dto.QCompanyReviewDto;
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

    @Override
    public CompanyDetailResponse findCompanyDetailInfo(Long companyId) {
        List<String> atmospheres =
                jpaQueryFactory
                        .select(companyAtmosphere.atmosphere.stringValue())
                        .from(companyAtmosphere)
                        .where(companyAtmosphere.company.id.eq(companyId))
                        .fetch();

        List<CompanyHourDto> hours =
                jpaQueryFactory
                        .select(
                                new QCompanyHourDto(
                                        companyHour.dayOfWeek,
                                        companyHour.startTime,
                                        companyHour.endTime))
                        .from(companyHour)
                        .where(companyHour.company.id.eq(companyId))
                        .fetch();

        List<CompanyReviewDto> reviews =
                jpaQueryFactory
                        .select(
                                new QCompanyReviewDto(
                                        companyReview.member.profileUrl,
                                        companyReview.member.nickname,
                                        companyReview.reviewValue))
                        .from(companyReview)
                        .leftJoin(companyReview.member, member)
                        .where(companyReview.company.id.eq(companyId))
                        .fetch();

        List<String> types =
                jpaQueryFactory
                        .select(companyType.type.stringValue())
                        .from(companyType)
                        .where(companyType.company.id.eq(companyId))
                        .fetch();

        List<String> verifyImageUrls =
                jpaQueryFactory
                        .select(companySubLevel.verifyImageUrl)
                        .from(companySubLevel)
                        .where(companySubLevel.company.id.eq(companyId))
                        .fetch();
        return new CompanyDetailResponse(atmospheres, hours, reviews, types, verifyImageUrls);
    }
}
