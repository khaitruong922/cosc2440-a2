package s3818074_s3818487.cosc2440a2;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import s3818074_s3818487.cosc2440a2.controllers.StaffController;
import s3818074_s3818487.cosc2440a2.models.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StaffControllerUnitTest extends AbstractUnitTest<Staff>{
    @InjectMocks
    private StaffController controller;

    @BeforeEach
    public void init(){
        setUp(controller);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[POST] Create Staff")
    public void addStaffTest() throws Exception {}

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("[GET] Get Staffs")
    public void getStaffsTest() throws Exception {}

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("[GET] Get Staff")
    public void getStaffTest() throws Exception {}

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("[DELETE] Delete Staffs")
    public void deleteStaffsTest() throws Exception {}

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("[DELETE] Delete Staff")
    public void deleteStaffTest() throws Exception {}

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("[PUT] Update Staff")
    public void updateStaffTest() throws Exception {}
}
