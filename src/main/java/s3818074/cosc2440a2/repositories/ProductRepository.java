package s3818074.cosc2440a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s3818074.cosc2440a2.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}