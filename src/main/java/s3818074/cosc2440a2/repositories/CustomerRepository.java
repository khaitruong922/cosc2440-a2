package s3818074.cosc2440a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s3818074.cosc2440a2.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findAllByName(String name);

    List<Customer> findAllByAddress(String address);

    List<Customer> findAllByPhone(String phone);

}
