package s3818074.cosc2440a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import s3818074.cosc2440a2.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
