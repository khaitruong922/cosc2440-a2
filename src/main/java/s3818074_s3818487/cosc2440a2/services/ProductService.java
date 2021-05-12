package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Category;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.repositories.CategoryRepository;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductService extends AbstractService<Product, UUID> {
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository repo, CategoryRepository categoryRepository) {
        super(repo);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product add(Product product) {
        Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
        if (category.isEmpty()) return null;
        product.setCategory(category.get());
        category.get().addProduct(product);
        return super.add(product);
    }

}
