package uz.pdp.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcompany.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
