package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}

