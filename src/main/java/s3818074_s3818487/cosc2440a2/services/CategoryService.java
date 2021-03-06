package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Category;
import s3818074_s3818487.cosc2440a2.repositories.CategoryRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class CategoryService extends AbstractService<Category, UUID> {

    @Autowired
    public CategoryService(CategoryRepository repo) {
        super(repo);
    }

}
