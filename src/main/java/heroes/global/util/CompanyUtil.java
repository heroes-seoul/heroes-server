package heroes.global.util;

import heroes.domain.company.dao.CompanyRepository;
import heroes.domain.company.domain.Company;
import heroes.domain.member.dao.MemberRepository;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyUtil {
    private final MemberUtil memberUtil;
    private final CompanyRepository companyRepository;

    public Company getCurrentCompany() {
        return companyRepository
                .findById(memberUtil.getCurrentMember().getCompany().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));
    }
}
