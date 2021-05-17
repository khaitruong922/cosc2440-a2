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
    public Staff updateById(Staff staff, UUID id) {
        Staff targetedStaff = repo.getOne(id);
        targetedStaff.setAddress(Optional.of(staff.getAddress()).orElse(targetedStaff.getAddress()));
        targetedStaff.setEmail(Optional.of(staff.getEmail()).orElse(targetedStaff.getEmail()));
        targetedStaff.setName(Optional.of(staff.getName()).orElse(targetedStaff.getName()));
        targetedStaff.setPhone(Optional.of(staff.getPhone()).orElse(targetedStaff.getPhone()));

        return repo.save(targetedStaff);
    }
}

