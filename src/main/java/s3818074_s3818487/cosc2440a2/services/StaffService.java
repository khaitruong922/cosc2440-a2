package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StaffService extends AbstractService<Staff, UUID> {
    @Autowired
    public StaffService(StaffRepository repo) {
        super(repo);
    }

    @Override
    public Staff updateById(Staff updatedStaff, UUID id) {
        Optional<Staff> staffOptional = repo.findById(id);
        if (staffOptional.isEmpty()) throw new RuntimeException("Staff not found!");
        Staff staff = staffOptional.get();
        staff.setAddress(Optional.ofNullable(updatedStaff.getAddress()).orElse(staff.getAddress()));
        staff.setEmail(Optional.ofNullable(updatedStaff.getEmail()).orElse(staff.getEmail()));
        staff.setName(Optional.ofNullable(updatedStaff.getName()).orElse(staff.getName()));
        staff.setPhone(Optional.ofNullable(updatedStaff.getPhone()).orElse(staff.getPhone()));
        staff.setContactPerson(Optional.ofNullable(updatedStaff.getContactPerson()).orElse(staff.getContactPerson()));

        return staff;
    }
}

