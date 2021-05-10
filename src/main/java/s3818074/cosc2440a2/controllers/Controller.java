package s3818074.cosc2440a2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import s3818074.cosc2440a2.services.AbstractService;

import java.util.List;
import java.util.UUID;

public interface Controller<T> {

    @GetMapping
    List<T> getAll();

    @GetMapping("/{id}")
    T getById(@PathVariable("id") UUID id);

    @PostMapping
    public T add(@RequestBody T t);

    @DeleteMapping("/{id}")
    public HttpStatus deleteById(@PathVariable("id") UUID id);

    @DeleteMapping
    public HttpStatus deleteAll();
}
