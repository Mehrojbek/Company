package uz.pdp.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcompany.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    boolean existsByNameAndCompanyId(String name, Integer company_id);
    Department findByNameAndCompanyId(String name, Integer company_id);
    boolean existsByNameAndCompanyIdAndIdNot(String name, Integer company_id, Integer id);
}
