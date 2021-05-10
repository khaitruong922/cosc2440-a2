package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;
import s3818074_s3818487.cosc2440a2.representations.ProductResponse;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService extends AbstractService<Product, UUID> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository repo) {
        super(repo);
        this.productRepository = repo;
    }

    public List<ProductResponse> getAllJoinInfo() {
        return productRepository.getJoinInfo();
    }

}
