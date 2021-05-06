package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import s3818074.cosc2440a2.models.Category;
import s3818074.cosc2440a2.models.Product;
import s3818074.cosc2440a2.repositories.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping()
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @PostMapping()
    public Category add(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
}
