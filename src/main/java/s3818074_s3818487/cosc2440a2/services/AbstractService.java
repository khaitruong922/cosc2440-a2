package s3818074_s3818487.cosc2440a2.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T, ID> {
    private static final int RESULTS_PER_PAGE = 5;

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

    public List<T> getAllByPage(int page) {
        return repo.findAll(PageRequest.of(page, RESULTS_PER_PAGE)).toList();
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
