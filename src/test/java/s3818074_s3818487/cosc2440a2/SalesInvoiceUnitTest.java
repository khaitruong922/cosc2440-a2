package s3818074_s3818487.cosc2440a2;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import s3818074_s3818487.cosc2440a2.controllers.SalesInvoiceController;
import s3818074_s3818487.cosc2440a2.models.*;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SalesInvoiceUnitTest extends AbstractUnitTest<SalesInvoice>{
    @InjectMocks
    private SalesInvoiceController controller;

    public SalesInvoiceUnitTest() {
        super("sales-invoices");
    }

    @BeforeEach
    public void init(){
        setUp(controller);
    }

    @Override
    SalesInvoice populateData() {
        Category category = new Category(genUUID(),"bike");
        Product product = new Product(genUUID(),"bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",category,25.5);
        Staff staff = new Staff(genUUID(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        Customer customer = new Customer(genUUID(),"Tin Customer", "123 ABC",
                "0909090888", "123","admin@email.com","Chung Quan Tin");
        return new SalesInvoice(genUUID(),new Date(),staff,customer,Arrays.asList(
                new SalesDetail(genUUID(),product, 100, 15),
                new SalesDetail(genUUID(),product, 100, 15)
        ), 3000);
    }

    @Override
    List<SalesInvoice> populateListOfData() {
        Category category = new Category(genUUID(),"bike");
        Product product = new Product(genUUID(),"bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",category,25.5);
        Staff staff = new Staff(genUUID(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        Customer customer = new Customer(genUUID(),"Tin Customer", "123 ABC",
                "0909090888", "123","admin@email.com","Chung Quan Tin");
        return Arrays.asList(
                new SalesInvoice(genUUID(),new Date(),staff,customer,Arrays.asList(
                        new SalesDetail(genUUID(),product, 100, 15),
                        new SalesDetail(genUUID(),product, 100, 15)
                ), 3000),
                new SalesInvoice(genUUID(),new Date(),staff,customer,Arrays.asList(
                        new SalesDetail(genUUID(),product, 100, 15),
                        new SalesDetail(genUUID(),product, 100, 15)
                ), 3000),
                new SalesInvoice(genUUID(),new Date(),staff,customer,Arrays.asList(
                        new SalesDetail(genUUID(),product, 100, 15),
                        new SalesDetail(genUUID(),product, 100, 15)
                ), 3000),
                new SalesInvoice(genUUID(),new Date(),staff,customer,Arrays.asList(
                        new SalesDetail(genUUID(),product, 100, 15),
                        new SalesDetail(genUUID(),product, 100, 15)
                ), 3000)
        );
    }
}
