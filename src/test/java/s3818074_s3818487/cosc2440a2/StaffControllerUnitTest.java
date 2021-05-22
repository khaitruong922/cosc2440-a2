package s3818074_s3818487.cosc2440a2;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import s3818074_s3818487.cosc2440a2.controllers.StaffController;
import s3818074_s3818487.cosc2440a2.models.*;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class StaffControllerUnitTest extends AbstractUnitTest<Staff>{
    @InjectMocks
    private StaffController controller;

    public StaffControllerUnitTest() {
        super("staffs");
    }

    @BeforeEach
    public void init(){
        setUp(controller);
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
}
