package heroes.domain.bookmark.application;

import heroes.domain.bookmark.dao.CompanyBookmarkRepository;
import heroes.domain.bookmark.domain.CompanyBookmark;
import heroes.domain.company.dao.CompanyRepository;
import heroes.domain.company.domain.Company;
import heroes.domain.member.domain.Member;
import heroes.global.error.exception.CustomException;
import heroes.global.error.exception.ErrorCode;
import heroes.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyBookmarkService {
    private final MemberUtil memberUtil;
    private final CompanyRepository companyRepository;
    private final CompanyBookmarkRepository bookmarkRepository;

    public void createCompanyBookmark(Long companyId) {
        final Member currentMember = memberUtil.getCurrentMember();

        Company company =
                companyRepository
                        .findById(companyId)
                        .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));
        validateBookmarkExist(currentMember, company);
        bookmarkRepository.save(CompanyBookmark.create(company, currentMember));
    }

    private void validateBookmarkExist(Member currentMember, Company company) {
        if (bookmarkRepository.existsByCompanyAndMember(company, currentMember)) {
            throw new CustomException(ErrorCode.COMPANY_BOOKMARK_ALREADY_EXIST);
        }
    }
}
