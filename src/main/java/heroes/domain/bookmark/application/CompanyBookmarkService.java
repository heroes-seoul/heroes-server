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

        Company company = findCompanyById(companyId);
        validateBookmarkExist(currentMember, company);
        bookmarkRepository.save(CompanyBookmark.create(company, currentMember));
    }

    public void deleteCompanyBookmark(Long companyId) {
        final Member currentMember = memberUtil.getCurrentMember();

        Company company = findCompanyById(companyId);
        CompanyBookmark bookmark =
                bookmarkRepository
                        .findByCompanyAndMember(company, currentMember)
                        .orElseThrow(
                                () -> new CustomException(ErrorCode.COMPANY_BOOKMARK_NOT_FOUND));
        bookmarkRepository.delete(bookmark.remove());
    }

    private Company findCompanyById(Long companyId) {
        return companyRepository
                .findById(companyId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));
    }

    private void validateBookmarkExist(Member currentMember, Company company) {
        if (bookmarkRepository.existsByCompanyAndMember(company, currentMember)) {
            throw new CustomException(ErrorCode.COMPANY_BOOKMARK_ALREADY_EXIST);
        }
    }
}
