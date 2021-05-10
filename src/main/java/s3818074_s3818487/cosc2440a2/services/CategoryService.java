package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Category;
import s3818074_s3818487.cosc2440a2.repositories.CategoryRepository;

import java.util.UUID;

@Service
public class CategoryService extends AbstractService<Category, UUID> {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository repo) {
        super(repo);
        this.categoryRepository = repo;
    }

}
