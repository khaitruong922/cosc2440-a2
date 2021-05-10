package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.Provider;
import s3818074_s3818487.cosc2440a2.services.ProviderService;

import java.util.UUID;

@RestController
@RequestMapping("/providers")
public class ProviderController extends AbstractController<Provider, UUID> {

    @Autowired
    public ProviderController(ProviderService service) {
        super(service);
    }
}