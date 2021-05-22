package s3818074_s3818487.cosc2440a2;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import s3818074_s3818487.cosc2440a2.controllers.DeliveryNoteController;
import s3818074_s3818487.cosc2440a2.models.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeliveryNoteUnitTest extends AbstractUnitTest<DeliveryNote>{
    @InjectMocks
    private DeliveryNoteController controller;

    public DeliveryNoteUnitTest() {
        super("delivery-notes");
    }

    @BeforeEach
    public void init(){
        setUp(controller);
    }


    @Override
    protected DeliveryNote populateData() {
        Category c1 = new Category(uuid(),"bike");
        Category c2 = new Category(uuid(),"book");
        Product p1 = new Product(uuid(),"bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",c1,25.5);
        Product p2 = new Product(uuid(),"book for kid", "BK5","BOOKA",
                "BookForPeace","This is a book",c2,25.5);
        Staff staff = new Staff(uuid(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        return new DeliveryNote(uuid(),new Date(), staff, Arrays.asList(
                new DeliveryDetail(uuid(),p1, 10),
                new DeliveryDetail(uuid(), p2 , 10)
        ));
    }

    @Override
    protected List<DeliveryNote> populateListOfData() {
        Category c1 = new Category(uuid(),"bike");
        Category c2 = new Category(uuid(),"book");
        Product p1 = new Product(uuid(),"bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",c1,25.5);
        Product p2 = new Product(uuid(),"book for kid", "BK5","BOOKA",
                "BookForPeace","This is a book",c2,25.5);
        Staff staff = new Staff(uuid(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        return Arrays.asList(
                new DeliveryNote(uuid(),new Date(), staff, Arrays.asList(
                        new DeliveryDetail(uuid(),p1, 10),
                        new DeliveryDetail(uuid(), p2 , 10)
                )),
                new DeliveryNote(uuid(),new Date(), staff, Arrays.asList(
                        new DeliveryDetail(uuid(),p1, 10),
                        new DeliveryDetail(uuid(), p2 , 10)
                )),
                new DeliveryNote(uuid(),new Date(), staff, Arrays.asList(
                        new DeliveryDetail(uuid(),p1, 10),
                        new DeliveryDetail(uuid(), p2 , 10)
                )),
                new DeliveryNote(uuid(),new Date(), staff, Arrays.asList(
                        new DeliveryDetail(uuid(),p1, 10),
                        new DeliveryDetail(uuid(), p2 , 10)
                )),
                new DeliveryNote(uuid(),new Date(), staff, Arrays.asList(
                        new DeliveryDetail(uuid(),p1, 10),
                        new DeliveryDetail(uuid(), p2 , 10)
                ))
        );
    }
}



