package uz.pdp.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcompany.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {
        boolean existsByPhoneNumber(String phoneNumber);
        long existsByPhoneNumberStartingWithAndPhoneNumberEndingWith(String deleted, String phoneNumber);
        boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
