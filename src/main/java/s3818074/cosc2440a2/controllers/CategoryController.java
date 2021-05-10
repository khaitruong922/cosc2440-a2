package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074.cosc2440a2.models.Category;
import s3818074.cosc2440a2.services.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController implements Controller<Category> {
    @Autowired
    protected CategoryService service;

    @Override
    public List<Category> getAll() {
        return service.getAll();
    }

    @Override
    public Category getById(UUID id) {
        return service.getById(id);
    }

    @Override
    public Category add(Category category) {
        return service.add(category);
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
