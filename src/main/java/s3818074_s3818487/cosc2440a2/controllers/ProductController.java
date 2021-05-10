package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.representations.ProductCreateDto;
import s3818074_s3818487.cosc2440a2.representations.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductController{
    @GetMapping
    List<ProductResponse> getAll();

    @GetMapping("/{id}")
    Product getById(@PathVariable("id") UUID id);

    @PostMapping
    Product add(@RequestBody ProductCreateDto t);

    @DeleteMapping("/{id}")
    HttpStatus deleteById(@PathVariable("id") UUID id);

    @DeleteMapping
    HttpStatus deleteAll();
}
