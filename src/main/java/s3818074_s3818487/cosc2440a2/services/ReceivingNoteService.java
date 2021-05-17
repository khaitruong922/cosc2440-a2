package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingNoteRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ReceivingNoteService extends AbstractService<ReceivingNote, UUID> {
    private final StaffRepository staffRepository;

    @Autowired
    public ReceivingNoteService(ReceivingNoteRepository repo, StaffRepository staffRepository) {
        super(repo);
        this.staffRepository = staffRepository;
    }

    @Override
    public ReceivingNote add(ReceivingNote receivingNote) {
        Optional<Staff> staffOptional = staffRepository.findById(receivingNote.getStaff().getId());
        if (staffOptional.isEmpty()) return null;
        receivingNote.setStaff(staffOptional.get());
        return super.add(receivingNote);
    }
}
