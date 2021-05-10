package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.Category;
import s3818074_s3818487.cosc2440a2.services.CategoryService;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController extends AbstractController<Category,UUID>{

    @Autowired
    public CategoryController(CategoryService service) {
        super(service);
    }
}
