package s3818074_s3818487.cosc2440a2;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import s3818074_s3818487.cosc2440a2.controllers.ReceivingNoteController;
import s3818074_s3818487.cosc2440a2.models.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReceivingNoteUnitTest extends AbstractUnitTest<ReceivingNote>{
    @InjectMocks
    private ReceivingNoteController controller;

    public ReceivingNoteUnitTest() {
        super("receiving-notes");
    }

    @BeforeEach
    public void init(){
        setUp(controller);
    }

    @Override
    ReceivingNote populateData() {
        Category c1 = new Category(genUUID(),"bike");
        Category c2 = new Category(genUUID(),"book");
        Product p1 = new Product(genUUID(),"bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",c1,25.5);
        Product p2 = new Product(genUUID(),"book for kid", "BK5","BOOKA",
                "BookForPeace","This is a book",c2,25.5);
        Staff staff = new Staff(genUUID(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        return new ReceivingNote(genUUID(),new Date(), staff, Arrays.asList(
                new ReceivingDetail(genUUID(),p1, 10),
                new ReceivingDetail(genUUID(), p2 , 10)
        ));
    }

    @Override
    List<ReceivingNote> populateListOfData() {
        Category c1 = new Category(genUUID(),"bike");
        Category c2 = new Category(genUUID(),"book");
        Product p1 = new Product(genUUID(),"bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",c1,25.5);
        Product p2 = new Product(genUUID(),"book for kid", "BK5","BOOKA",
                "BookForPeace","This is a book",c2,25.5);
        Staff staff = new Staff(genUUID(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        return Arrays.asList(
                new ReceivingNote(genUUID(),new Date(), staff, Arrays.asList(
                        new ReceivingDetail(genUUID(),p1, 10),
                        new ReceivingDetail(genUUID(), p2 , 10)
                )),
                new ReceivingNote(genUUID(),new Date(), staff, Arrays.asList(
                        new ReceivingDetail(genUUID(),p1, 10),
                        new ReceivingDetail(genUUID(), p2 , 10)
                )),
                new ReceivingNote(genUUID(),new Date(), staff, Arrays.asList(
                        new ReceivingDetail(genUUID(),p1, 10),
                        new ReceivingDetail(genUUID(), p2 , 10)
                )),
                new ReceivingNote(genUUID(),new Date(), staff, Arrays.asList(
                        new ReceivingDetail(genUUID(),p1, 10),
                        new ReceivingDetail(genUUID(), p2 , 10)
                )),
                new ReceivingNote(genUUID(),new Date(), staff, Arrays.asList(
                        new ReceivingDetail(genUUID(),p1, 10),
                        new ReceivingDetail(genUUID(), p2 , 10)
                ))
        );
    }
}



