package s3818074_s3818487.cosc2440a2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import s3818074_s3818487.cosc2440a2.controllers.StaffController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;
import s3818074_s3818487.cosc2440a2.services.StaffService;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StaffControllerUnitTest extends AbstractUnitTest<Staff>{
    @InjectMocks
    @Autowired
    private StaffController controller;

    public StaffControllerUnitTest() {
        super("staffs");
    }

    @MockBean
    protected StaffRepository repository;

    @Autowired
    protected StaffService service;

    @BeforeEach
    public void init(){
        setUp(controller, service, repository);
    }

    @Override
    protected Staff populateData() {
        return new Staff(uuid(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
    }

    @Override
    protected List<Staff> populateListOfData() {
        return Arrays.asList(
                new Staff(uuid(),"Tin Staff", "123 ABC", "0909090888",
                        "admin@email.com","Chung Quan Tin"),
                new Staff(uuid(),"Khai Staff", "123 ABC", "0909090888",
                        "admin@email.com","Truong Duc Khai")
        );
    }

    @Override
    public void updateByIdTestWebLayerThrowDataNotFound(String name) {
        super.updateByIdTestWebLayerThrowDataNotFound("Staff");
    }
}
