package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import s3818074.cosc2440a2.models.Product;
import s3818074.cosc2440a2.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping()
    public List<Product> getAll(){
        return productRepository.findAll();
    }
    @PostMapping()
    public Product add(@RequestBody Product product){
        return productRepository.save(product);
    }

}
