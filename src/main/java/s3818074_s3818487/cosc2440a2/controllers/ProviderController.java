package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import s3818074_s3818487.cosc2440a2.models.Provider;

import java.util.List;
import java.util.UUID;

public interface ProviderController {
    @GetMapping
    List<Provider> getAll();

    @GetMapping("/{id}")
    Provider getById(@PathVariable("id") UUID id);

    @PostMapping
    Provider add(@RequestBody Provider t);

    @DeleteMapping("/{id}")
    HttpStatus deleteById(@PathVariable("id") UUID id);

    @DeleteMapping
    HttpStatus deleteAll();
}
