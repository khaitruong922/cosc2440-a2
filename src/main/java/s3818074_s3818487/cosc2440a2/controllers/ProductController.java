package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.services.ProductService;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController extends AbstractController<Product, UUID> {
    @Autowired
    public ProductController(ProductService service) {
        super(service);
    }

}
