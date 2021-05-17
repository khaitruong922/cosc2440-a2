package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.services.StaffService;

import java.util.UUID;

@RestController
@RequestMapping("/staffs")
public class StaffController extends AbstractController<Staff, UUID> {

    @Autowired
    public StaffController(StaffService service) {
        super(service);
    }
}
