package s3818074.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074.cosc2440a2.models.Category;
import s3818074.cosc2440a2.repositories.CategoryRepository;
import java.util.UUID;

@Service
public class CategoryService extends AbstractService<Category, UUID> {
    @Autowired
    public CategoryService(CategoryRepository repo) {
        super(repo);
    }
}
