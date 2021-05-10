package s3818074_s3818487.cosc2440a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.representations.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT new s3818074_s3818487.cosc2440a2.representations.ProductResponse(p,c) FROM Product p JOIN p.category c")
    public List<ProductResponse> getJoinInfo();
}
