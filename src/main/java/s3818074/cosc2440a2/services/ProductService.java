package s3818074.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074.cosc2440a2.models.Product;
import s3818074.cosc2440a2.repositories.ProductRepository;

import java.util.UUID;

@Service
public class ProductService extends AbstractService<Product, UUID> {
    @Autowired
    public ProductService(ProductRepository repo) {
        super(repo);
    }
}
