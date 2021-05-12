package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.DeliveryNote;
import s3818074_s3818487.cosc2440a2.repositories.DeliveryNoteRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class DeliveryNoteService extends AbstractService<DeliveryNote, UUID>{
    @Autowired
    public DeliveryNoteService(DeliveryNoteRepository repo) {
        super(repo);
    }
}

