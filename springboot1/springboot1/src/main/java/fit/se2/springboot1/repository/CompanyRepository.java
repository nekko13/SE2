package fit.se2.springboot1.repository;

import fit.se2.springboot1.model.Company;
import fit.se2.springboot1.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
