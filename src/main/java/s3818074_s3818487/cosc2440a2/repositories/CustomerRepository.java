package s3818074_s3818487.cosc2440a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import s3818074_s3818487.cosc2440a2.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findAllByName(String name);

    List<Customer> findAllByAddress(String address);

    List<Customer> findAllByPhone(String phone);
}
