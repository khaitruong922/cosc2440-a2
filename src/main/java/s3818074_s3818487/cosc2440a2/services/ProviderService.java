package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Provider;
import s3818074_s3818487.cosc2440a2.repositories.ProviderRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class ProviderService extends AbstractService<Provider, UUID> {
    @Autowired
    public ProviderService(ProviderRepository repo) {
        super(repo);
    }
}
