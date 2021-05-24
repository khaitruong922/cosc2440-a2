package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import s3818074_s3818487.cosc2440a2.filters.DeliveryNoteFilter;
import s3818074_s3818487.cosc2440a2.models.DeliveryDetail;
import s3818074_s3818487.cosc2440a2.models.DeliveryNote;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.DeliveryDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.DeliveryNoteRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class DeliveryNoteService extends AbstractService<DeliveryNote, UUID> {
    private final StaffRepository staffRepository;
    private final DeliveryDetailRepository deliveryDetailRepository;

    @Autowired
    public DeliveryNoteService(DeliveryNoteRepository repo, StaffRepository staffRepository, DeliveryDetailRepository deliveryDetailRepository) {
        super(repo);
        this.staffRepository = staffRepository;
        this.deliveryDetailRepository = deliveryDetailRepository;
    }

    @Override
    public DeliveryNote add(DeliveryNote deliveryNote) {
        // Handle staff
        Staff staff = deliveryNote.getStaff();
        if (staff == null) throw new RuntimeException("Missing staff argument!");
        Optional<Staff> staffOptional = staffRepository.findById(staff.getId());
        if (staffOptional.isEmpty()) throw new RuntimeException("Staff not found!");
        deliveryNote.setStaff(staffOptional.get());

        // Handle delivery details
        List<DeliveryDetail> deliveryDetails = new ArrayList<>();
        if (deliveryNote.getDeliveryDetails() == null) deliveryNote.setDeliveryDetails(Collections.emptyList());
        deliveryNote.getDeliveryDetails().forEach(dd -> {
            Optional<DeliveryDetail> deliveryDetailOptional = deliveryDetailRepository.findById(dd.getId());
            if (deliveryDetailOptional.isEmpty()) throw new RuntimeException("Delivery detail not found!");
            DeliveryDetail deliveryDetail = deliveryDetailOptional.get();
            if (deliveryDetail.getDeliveryNote() != null)
                throw new RuntimeException("Delivery detail " + deliveryDetail.getId() + " has been used!");
            deliveryDetail.setDeliveryNote(deliveryNote);
            deliveryDetails.add(deliveryDetail);
        });
        deliveryNote.setDeliveryDetails(deliveryDetails);
        return super.add(deliveryNote);
    }

    @Override
    public DeliveryNote updateById(DeliveryNote updatedDeliveryNote, UUID id) {
        Optional<DeliveryNote> deliveryNoteOptional = repo.findById(id);
        if (deliveryNoteOptional.isEmpty()) throw new RuntimeException("Delivery note not found!");
        DeliveryNote deliveryNote = deliveryNoteOptional.get();

        // Handle delivery details update
        if (updatedDeliveryNote.getDeliveryDetails() != null) {
            List<DeliveryDetail> deliveryDetails = new ArrayList<>();
            updatedDeliveryNote.getDeliveryDetails().forEach(dd -> {
                // Set the note of all current details to null
                deliveryNote.getDeliveryDetails().forEach(d -> d.setDeliveryNote(null));

                Optional<DeliveryDetail> deliveryDetailOptional = deliveryDetailRepository.findById(dd.getId());
                if (deliveryDetailOptional.isEmpty()) throw new RuntimeException("Delivery detail not found");
                DeliveryDetail deliveryDetail = deliveryDetailOptional.get();
                // Check if the delivery detail is null
                if (deliveryDetail.getDeliveryNote() != null)
                    throw new RuntimeException("Delivery detail " + deliveryDetail.getId() + " has been used!");

                deliveryDetail.setDeliveryNote(deliveryNote);
                deliveryDetails.add(deliveryDetail);
            });
            deliveryNote.setDeliveryDetails(deliveryDetails);
        }

        // Handle staff update
        if (updatedDeliveryNote.getStaff() != null) {
            Optional<Staff> staffOptional = staffRepository.findById(updatedDeliveryNote.getStaff().getId());
            deliveryNote.setStaff(staffOptional.orElse(deliveryNote.getStaff()));
        }

        // Handle date update
        deliveryNote.setDate(Optional.ofNullable(deliveryNote.getDate()).orElse(deliveryNote.getDate()));

        return deliveryNote;
    }

    public List<DeliveryNote> search(Date startDate, Date endDate, Integer page) {
        return new DeliveryNoteFilter(super.getAll(page)).start(startDate).end(endDate).get();
    }
}

