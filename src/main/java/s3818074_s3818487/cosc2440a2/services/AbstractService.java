package s3818074_s3818487.cosc2440a2.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import s3818074_s3818487.cosc2440a2.models.BaseEntity;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T extends BaseEntity, ID> {
    private static final int RESULTS_PER_PAGE = 5;

    protected JpaRepository<T, ID> repo;

    protected AbstractService(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    public List<T> getAll() {
        return repo.findAll();
    }

    public List<T> getAll(Integer page) {
        if (page == null) return getAll();
        return repo.findAll(PageRequest.of(page, RESULTS_PER_PAGE)).toList();
    }

    @Transactional
    public T add(T t) {
        return repo.save(t);
    }

    @Transactional
    public T getById(ID id) {
        Optional<T> t = repo.findById(id);
        if (t.isEmpty()) return null;
        return t.get();
    }

    @Transactional
    public HttpStatus deleteById(ID id) {
        try {
            repo.deleteById(id);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Transactional
    public HttpStatus deleteAll() {
        try {
            repo.deleteAll();
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public T updateById(T t, ID id) {
        return null;
    }
}
