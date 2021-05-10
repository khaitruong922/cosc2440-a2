package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.Category;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.representations.ProductCreateDto;
import s3818074_s3818487.cosc2440a2.representations.ProductResponse;
import s3818074_s3818487.cosc2440a2.services.CategoryService;
import s3818074_s3818487.cosc2440a2.services.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductControllerImpl implements ProductController{
    @Autowired
    protected ProductService productService;

    @Autowired
    protected CategoryService categoryService;

    public List<ProductResponse> getAll() {
        return productService.getAllJoinInfo();
    }

    @Override
    public Product getById(UUID id) {
        return productService.getById(id);
    }

    @Override
    public Product add(ProductCreateDto productCreateDto) {
        Category category = categoryService.getById(productCreateDto.categoryId);
        productCreateDto.product.setCategory(category);
        category.getProducts().add(productCreateDto.product);
        return productService.add(productCreateDto.product);
    }

    @Override
    public HttpStatus deleteById(UUID id) {
        return productService.deleteById(id);
    }

    @Override
    public HttpStatus deleteAll() {
        return productService.deleteAll();
    }
}
