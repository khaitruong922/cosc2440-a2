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
    protected SalesInvoice populateData() {
        Category category = new Category(uuid(),"bike");
        Product product = new Product(uuid(),"bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",category,25.5);
        Staff staff = new Staff(uuid(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        Customer customer = new Customer(uuid(),"Tin Customer", "123 ABC",
                "0909090888", "123","admin@email.com","Chung Quan Tin");
        return new SalesInvoice(uuid(),new Date(),staff,customer,Arrays.asList(
                new SalesDetail(uuid(),product, 100, 15),
                new SalesDetail(uuid(),product, 100, 15)
        ), 3000.0);
    }

    @Override
    protected List<SalesInvoice> populateListOfData() {
        Category category = new Category(uuid(),"bike");
        Product product = new Product(uuid(),"bike for kid", "BK3","BKA",
                "BikeForPeace","This is a bike",category,25.5);
        Staff staff = new Staff(uuid(),"Tin Staff", "123 ABC", "0909090888",
                "admin@email.com","Chung Quan Tin");
        Customer customer = new Customer(uuid(),"Tin Customer", "123 ABC",
                "0909090888", "123","admin@email.com","Chung Quan Tin");
        return Arrays.asList(
                new SalesInvoice(uuid(),new Date(),staff,customer,Arrays.asList(
                        new SalesDetail(uuid(),product, 100, 15),
                        new SalesDetail(uuid(),product, 100, 15)
                ), 3000.0),
                new SalesInvoice(uuid(),new Date(),staff,customer,Arrays.asList(
                        new SalesDetail(uuid(),product, 100, 15),
                        new SalesDetail(uuid(),product, 100, 15)
                ), 3000.0),
                new SalesInvoice(uuid(),new Date(),staff,customer,Arrays.asList(
                        new SalesDetail(uuid(),product, 100, 15),
                        new SalesDetail(uuid(),product, 100, 15)
                ), 3000.0),
                new SalesInvoice(uuid(),new Date(),staff,customer,Arrays.asList(
                        new SalesDetail(uuid(),product, 100, 15),
                        new SalesDetail(uuid(),product, 100, 15)
                ), 3000.0)
        );
    }
}
