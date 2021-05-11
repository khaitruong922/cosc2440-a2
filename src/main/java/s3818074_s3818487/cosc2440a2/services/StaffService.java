package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import java.util.UUID;

@Service
public class StaffService extends AbstractService<Staff, UUID> {
    @Autowired
    public StaffService(StaffRepository repo) {
        super(repo);
    }

    @Override
    public HttpStatus updateById(Staff staff, UUID id) {
        try {
            Staff targetedStaff = repo.getOne(id);
            targetedStaff.setAddress(staff.getAddress() != null ? staff.getAddress() : targetedStaff.getAddress());
            targetedStaff.setEmail(staff.getEmail() != null ? staff.getEmail() : targetedStaff.getEmail());
            targetedStaff.setName(staff.getName() != null ? staff.getName() : targetedStaff.getName());
            targetedStaff.setPhone(staff.getPhone() != null ? staff.getPhone() : targetedStaff.getPhone());
            repo.save(targetedStaff);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}

