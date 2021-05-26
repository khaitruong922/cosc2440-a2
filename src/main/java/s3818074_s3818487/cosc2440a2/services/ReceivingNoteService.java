package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.filters.DeliveryNoteFilter;
import s3818074_s3818487.cosc2440a2.filters.ReceivingNoteFilter;
import s3818074_s3818487.cosc2440a2.models.DeliveryNote;
import s3818074_s3818487.cosc2440a2.models.ReceivingDetail;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingNoteRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.transaction.Transactional;
import java.util.*;

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
        Staff staff = receivingNote.getStaff();
        if (staff == null) throw new RuntimeException("Missing staff argument!");
        Optional<Staff> staffOptional = staffRepository.findById(staff.getId());
        if (staffOptional.isEmpty()) throw new RuntimeException("Staff not found!");
        receivingNote.setStaff(staffOptional.get());


        // Handle receiving details
        List<ReceivingDetail> receivingDetails = new ArrayList<>();
        if (receivingNote.getReceivingDetails() == null) receivingNote.setReceivingDetails(Collections.emptyList());
        receivingNote.getReceivingDetails().forEach(rd -> {
            // Set the note of all current details to null
            receivingNote.getReceivingDetails().forEach(d -> d.setReceivingNote(null));

            Optional<ReceivingDetail> receivingDetailOptional = receivingDetailRepository.findById(rd.getId());
            if (receivingDetailOptional.isEmpty()) throw new RuntimeException("Receiving detail not found!");
            ReceivingDetail receivingDetail = receivingDetailOptional.get();
            if (receivingDetail.getReceivingNote() != null)
                throw new RuntimeException("Receiving detail " + receivingDetail.getId() + " has been used!");
            receivingDetail.setReceivingNote(receivingNote);
            receivingDetails.add(receivingDetail);
        });
        receivingNote.setReceivingDetails(receivingDetails);
        return super.add(receivingNote);
    }

    @Override
    public ReceivingNote updateById(ReceivingNote updatedReceivingNote, UUID id) {
        Optional<ReceivingNote> receivingNoteOptional = repo.findById(id);
        if (receivingNoteOptional.isEmpty()) throw new RuntimeException("Receiving note not found!");
        ReceivingNote receivingNote = receivingNoteOptional.get();

        // Handle receiving details update
        if (updatedReceivingNote.getReceivingDetails() != null) {
            List<ReceivingDetail> receivingDetails = new ArrayList<>();
            updatedReceivingNote.getReceivingDetails().forEach(rd -> {
                Optional<ReceivingDetail> receivingDetailOptional = receivingDetailRepository.findById(rd.getId());
                if (receivingDetailOptional.isEmpty()) throw new RuntimeException("Receiving detail not found");
                ReceivingDetail receivingDetail = receivingDetailOptional.get();
                // Check if the receiving detail does not belong to other order
                if (receivingDetail.getReceivingNote() != null && !receivingDetail.getReceivingNote().getId().equals(receivingNote.getId()))
                    throw new RuntimeException("Delivery detail " + receivingDetail.getId() + " has been used!");

                receivingDetail.setReceivingNote(receivingNote);
                receivingDetails.add(receivingDetail);
            });
            receivingNote.setReceivingDetails(receivingDetails);
        }

        // Handle staff update
        if (updatedReceivingNote.getStaff() != null) {
            Optional<Staff> staffOptional = staffRepository.findById(updatedReceivingNote.getStaff().getId());
            receivingNote.setStaff(staffOptional.orElse(receivingNote.getStaff()));
        }

        // Handle date update
        receivingNote.setDate(Optional.ofNullable(receivingNote.getDate()).orElse(receivingNote.getDate()));

        return receivingNote;
    }

    public List<ReceivingNote> search(Date startDate, Date endDate, Integer page) {
        return new ReceivingNoteFilter(super.getAll(page)).start(startDate).end(endDate).get();
    }
}
