package s3818074.cosc2440a2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import s3818074.cosc2440a2.services.AbstractService;

import java.util.List;

public class AbstractController<T, ID> {
    protected AbstractService<T, ID> service;

    public AbstractController(AbstractService<T, ID> service) {
        this.service = service;
    }

    @GetMapping
    public List<T> getAll() {
        return service.getAll();
    }

    @PostMapping
    public T add(@RequestBody T t) {
        return service.add(t);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteById(@PathVariable("id") ID id) {
        return service.deleteById(id);
    }

    @DeleteMapping
    public HttpStatus deleteAll(ID id) {
        return service.deleteAll();
    }
}
