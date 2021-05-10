package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import s3818074_s3818487.cosc2440a2.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerController{
    @GetMapping
    List<Customer> getAll();

    @GetMapping("/{id}")
    Customer getById(@PathVariable("id") UUID id);

    @PostMapping
    Customer add(@RequestBody Customer t);

    @DeleteMapping("/{id}")
    HttpStatus deleteById(@PathVariable("id") UUID id);

    @DeleteMapping
    HttpStatus deleteAll();
}
