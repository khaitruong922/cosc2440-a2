package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import s3818074.cosc2440a2.models.Product;
import s3818074.cosc2440a2.repositories.ProductRepository;
import s3818074.cosc2440a2.services.CategoryService;
import s3818074.cosc2440a2.services.ProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService s) {
        this.service = s;
    }

    @GetMapping()
    public List<Product> getAll() {
        return service.getAll();
    }

    @PostMapping()
    public Product add(@RequestBody Product product) {
        return service.add(product);
    }

}
