package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import s3818074_s3818487.cosc2440a2.models.BaseEntity;
import s3818074_s3818487.cosc2440a2.services.AbstractService;

import java.util.List;

public abstract class AbstractController<T extends BaseEntity, ID> {
    protected AbstractService<T, ID> service;

    public AbstractController(AbstractService<T, ID> service) {
        this.service = service;
    }

    @GetMapping
    List<T> getAll(@PathVariable(value = "page", required = false) Integer page) {
        return service.getAll(page);
    }

    @GetMapping("/{id}")
    T getById(@PathVariable("id") ID id) {
        return service.getById(id);
    }

    @PostMapping
    T add(@RequestBody T t) {
        return service.add(t);
    }

    @DeleteMapping("/{id}")
    HttpStatus deleteById(@PathVariable("id") ID id) {
        return service.deleteById(id);
    }

    @DeleteMapping
    HttpStatus deleteAll() {
        return service.deleteAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    T updateById(@RequestBody T t, @PathVariable("id") ID id) {
        return service.updateById(t, id);
    }

    ;
}
