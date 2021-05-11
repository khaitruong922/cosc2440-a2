package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import java.util.UUID;

@Service
public class StaffService extends AbstractService<Staff, UUID>{
    @Autowired
    public StaffService(StaffRepository repo) {
        super(repo);
    }
}

