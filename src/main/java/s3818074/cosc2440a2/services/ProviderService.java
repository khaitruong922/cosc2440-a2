package s3818074.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074.cosc2440a2.models.Provider;
import s3818074.cosc2440a2.repositories.ProviderRepository;

import java.util.UUID;

@Service
public class ProviderService extends AbstractService<Provider, UUID> {
    @Autowired
    public ProviderService(ProviderRepository repo) {
        super(repo);
    }
}
