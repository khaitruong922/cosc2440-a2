package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.ReceivingDetail;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingNoteRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceivingNoteService extends AbstractService<ReceivingNote, UUID> {
    private final StaffRepository staffRepository;
    private final ReceivingDetailRepository receivingDetailRepository;

    @Autowired
    public ReceivingNoteService(ReceivingNoteRepository repo, StaffRepository staffRepository, ProductRepository productRepository, ReceivingDetailRepository receivingDetailRepository) {
        super(repo);
        this.staffRepository = staffRepository;
        this.receivingDetailRepository = receivingDetailRepository;
    }

    @Override
    public ReceivingNote add(ReceivingNote receivingNote) {
        // Handle staff
        Optional<Staff> staffOptional = staffRepository.findById(receivingNote.getStaff().getId());
        if (staffOptional.isEmpty()) throw new Error("Staff does not exist!");
        receivingNote.setStaff(staffOptional.get());
        List<ReceivingDetail> receivingDetails = new ArrayList<>();
        // Handle receiving details
        receivingNote.getReceivingDetails().forEach(rd -> {
            Optional<ReceivingDetail> receivingDetailOptional = receivingDetailRepository.findById(rd.getId());
            if (receivingDetailOptional.isEmpty()) throw new Error("Receiving detail does not exist");
            ReceivingDetail receivingDetail = receivingDetailOptional.get();
            if (receivingDetail.getReceivingNote() != null)
                throw new Error("Receiving detail is already belong to a receiving note");
            receivingDetail.setReceivingNote(receivingNote);
            receivingDetails.add(receivingDetail);
        });
        receivingNote.setReceivingDetails(receivingDetails);
        return super.add(receivingNote);
    }
}
