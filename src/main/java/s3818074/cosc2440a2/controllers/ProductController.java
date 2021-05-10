package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074.cosc2440a2.models.Customer;
import s3818074.cosc2440a2.models.Product;
import s3818074.cosc2440a2.services.CustomerService;
import s3818074.cosc2440a2.services.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController implements Controller<Product> {
    @Autowired
    protected ProductService service;

    @Override
    public List<Product> getAll() {
        return service.getAll();
    }

    @Override
    public Product getById(UUID id) {
        return service.getById(id);
    }

    @Override
    public Product add(Product product) {
        return service.add(product);
    }

    @Override
    public HttpStatus deleteById(UUID id) {
        return service.deleteById(id);
    }

    @Override
    public HttpStatus deleteAll() {
        return service.deleteAll();
    }
}
