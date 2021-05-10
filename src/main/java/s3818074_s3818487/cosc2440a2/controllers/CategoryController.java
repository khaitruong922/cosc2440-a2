package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import s3818074_s3818487.cosc2440a2.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryController{
    @GetMapping
    List<Category> getAll();

    @GetMapping("/{id}")
    Category getById(@PathVariable("id") UUID id);

    @PostMapping
    Category add(@RequestBody Category t);

    @DeleteMapping("/{id}")
    HttpStatus deleteById(@PathVariable("id") UUID id);

    @DeleteMapping
    HttpStatus deleteAll();
}
