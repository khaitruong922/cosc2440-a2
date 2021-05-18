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
        Optional<Staff> staffOptional = staffRepository.findById(receivingNote.getStaff().getId());
        if (staffOptional.isEmpty()) return null;
        receivingNote.setStaff(staffOptional.get());
        List<ReceivingDetail> receivingDetails = receivingNote.getReceivingDetails();
        receivingDetails = receivingDetails.stream().filter(detail -> {
            Optional<ReceivingDetail> detailOptional = receivingDetailRepository.findById(detail.getId());
            return detailOptional.isPresent();
        }).collect(Collectors.toList());
        receivingNote.setReceivingDetails(receivingDetails);
        return super.add(receivingNote);
    }
}
