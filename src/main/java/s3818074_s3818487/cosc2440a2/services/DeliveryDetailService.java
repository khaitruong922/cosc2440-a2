package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.DeliveryDetail;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DeliveryDetailService extends AbstractService<DeliveryDetail, UUID> {
    private final ProductRepository productRepository;

    @Autowired
    protected DeliveryDetailService(JpaRepository<DeliveryDetail, UUID> repo, ProductRepository productRepository) {
        super(repo);
        this.productRepository = productRepository;
    }

    @Override
    public DeliveryDetail add(DeliveryDetail deliveryDetail) {
        Product product = deliveryDetail.getProduct();
        if (product == null) throw new RuntimeException("Missing product argument!");
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if (productOptional.isEmpty()) throw new RuntimeException("Product not found!");
        deliveryDetail.setProduct(productOptional.get());
        return super.add(deliveryDetail);
    }
}
