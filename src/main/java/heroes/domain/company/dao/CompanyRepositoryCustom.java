package heroes.domain.company.dao;

import heroes.domain.atmosphere.domain.Atmosphere;
import heroes.domain.company.domain.Company;
import heroes.domain.type.domain.Type;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CompanyRepositoryCustom {

    List<Company> findAllPage(Pageable pageable);

    Slice<Company> searchCompanies(
            String companyName,
            List<Type> types,
            List<Atmosphere> atmospheres,
            int pageSize,
            Long lastCompanyId);
}
