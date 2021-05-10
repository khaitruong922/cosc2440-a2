package s3818074.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074.cosc2440a2.models.Product;
import s3818074.cosc2440a2.models.Provider;
import s3818074.cosc2440a2.services.AbstractService;
import s3818074.cosc2440a2.services.ProductService;
import s3818074.cosc2440a2.services.ProviderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/providers")
public class ProviderController implements Controller<Provider> {
    @Autowired
    protected ProviderService service;

    @Override
    public List<Provider> getAll() {
        return service.getAll();
    }

    @Override
    public Provider getById(UUID id) {
        return service.getById(id);
    }

    @Override
    public Provider add(Provider provider) {
        return service.add(provider);
    }

    @Override
    public HttpStatus deleteById(UUID id) {
        return service.deleteById(id);
    }

    @Override
    public HttpStatus deleteAll() {
        return service.deleteAll();
    }
}
