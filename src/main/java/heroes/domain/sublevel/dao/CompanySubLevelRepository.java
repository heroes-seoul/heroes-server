package heroes.domain.sublevel.dao;

import heroes.domain.sublevel.domain.CompanySubLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySubLevelRepository extends JpaRepository<CompanySubLevel, Long> {}
