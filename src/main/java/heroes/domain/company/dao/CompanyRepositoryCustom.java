package heroes.domain.company.dao;

import heroes.domain.company.domain.Company;
import heroes.domain.company.dto.response.CompanyDetailResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

    List<Company> findAllPage(Pageable pageable);

    CompanyDetailResponse findCompanyDetailInfo(Long companyId);
}
