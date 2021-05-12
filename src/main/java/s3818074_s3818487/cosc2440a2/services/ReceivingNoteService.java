package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingNoteRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class ReceivingNoteService extends AbstractService<ReceivingNote, UUID>{
    @Autowired
    public ReceivingNoteService(ReceivingNoteRepository repo) {
        super(repo);
    }
}
