package s3818074.cosc2440a2.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, ID> {

    protected JpaRepository<T, ID> repo;

    protected AbstractService(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    public List<T> getAll() {
        return repo.findAll();
    }


    public T add(T t) {
        try {
            return repo.save(t);
        } catch (Exception e) {
            return null;
        }
    }

    public T getById(ID id) {
        Optional<T> t = repo.findById(id);
        if (t.isEmpty()) return null;
        return t.get();
    }

    public HttpStatus deleteById(ID id) {
        try {
            repo.deleteById(id);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus deleteAll() {
        try {
            repo.deleteAll();
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


}
