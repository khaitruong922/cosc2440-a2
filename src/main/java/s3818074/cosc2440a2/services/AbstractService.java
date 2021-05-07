package s3818074.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractService<T, ID> {

    protected JpaRepository<T, ID> repo;

    protected AbstractService(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    public List<T> getAll() {
        return repo.findAll();
    }


    public T add(T t) {
        return repo.save(t);
    }

}
