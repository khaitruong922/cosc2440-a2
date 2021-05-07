package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import s3818074.cosc2440a2.models.Category;
import s3818074.cosc2440a2.models.Product;
import s3818074.cosc2440a2.repositories.CategoryRepository;
import s3818074.cosc2440a2.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService s) {
        this.service = s;
    }

    @GetMapping()
    public List<Category> getAll() {
        return service.getAll();
    }

    @PostMapping()
    public Category add(@RequestBody Category category) {
        return service.add(category);
    }
}
