package s3818074_s3818487.cosc2440a2;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import s3818074_s3818487.cosc2440a2.controllers.WarehouseInfoController;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.DeliveryNoteRepository;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingNoteRepository;
import s3818074_s3818487.cosc2440a2.services.DeliveryNoteService;
import s3818074_s3818487.cosc2440a2.services.ProductService;
import s3818074_s3818487.cosc2440a2.services.ReceivingNoteService;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class WarehouseControllerUnitTest {
    @MockBean
    private ReceivingNoteRepository receivingNoteRepository;
    @MockBean
    private DeliveryNoteRepository deliveryNoteRepository;
    @MockBean
    private ProductRepository productRepository;

    @InjectMocks
    @Autowired
    private WarehouseInfoController warehouseInfoController;


    private UUID uuid() {
        return UUID.randomUUID();
    }

    @Test
    @DisplayName("[GET] Get warehouse info")
    public void getWarehouseInfoTest() {
        Product p1 = new Product(uuid(), "bike for kid", "BK3", "BKA",
                "BikeForPeace", "This is a bike", new Category(), 20);
        Product p2 = new Product(uuid(), "book for kid", "BK5", "BOOKA",
                "BookForPeace", "This is a book", new Category(), 25);
        Product p3 = new Product(uuid(), "book for kid", "BK5", "BOOKA",
                "BookForPeace", "This is a book", new Category(), 30);
        List<Product> products = Arrays.asList(p1, p2, p3);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        DeliveryDetail dd1 = new DeliveryDetail(uuid(), p1, 2);
        DeliveryDetail dd2 = new DeliveryDetail(uuid(), p2, 3);
        DeliveryDetail dd3 = new DeliveryDetail(uuid(), p3, 4);
        DeliveryDetail dd4 = new DeliveryDetail(uuid(), p1, 5);
        DeliveryNote dn1 = new DeliveryNote(uuid(), DateUtils.parse("2020-01-01"), new Staff(), Arrays.asList(dd1, dd2));
        DeliveryNote dn2 = new DeliveryNote(uuid(), DateUtils.parse("2020-03-01"), new Staff(), Arrays.asList(dd3, dd4));
        List<DeliveryNote> deliveryNotes = Arrays.asList(dn1, dn2);
        when(deliveryNoteRepository.findAll()).thenReturn(deliveryNotes);

        ReceivingDetail rd1 = new ReceivingDetail(uuid(), p1, 5);
        ReceivingDetail rd2 = new ReceivingDetail(uuid(), p2, 10);
        ReceivingDetail rd3 = new ReceivingDetail(uuid(), p3, 15);
        ReceivingDetail rd4 = new ReceivingDetail(uuid(), p1, 20);
        ReceivingNote rn1 = new ReceivingNote(uuid(), DateUtils.parse("2020-01-01"), new Staff(), Arrays.asList(rd1, rd2));
        ReceivingNote rn2 = new ReceivingNote(uuid(), DateUtils.parse("2020-03-01"), new Staff(), Arrays.asList(rd3, rd4));
        List<ReceivingNote> receivingNotes = Arrays.asList(rn1, rn2);
        when(receivingNoteRepository.findAll()).thenReturn(receivingNotes);

        Date startDate = DateUtils.parse("2020-01-01");
        Date endDate = DateUtils.parse("2020-02-01");
        Date specificDate = DateUtils.parse("2020-03-01");

        // Within a period
        List<WarehouseInfo> warehouseInfos1 = warehouseInfoController.getWarehouseInfos(startDate, endDate, null);
        Assertions.assertEquals(warehouseInfos1.size(), products.size());
        WarehouseInfo warehouseInfo1P1 = warehouseInfos1.stream().filter(i -> i.getProduct().getId().equals(p1.getId())).findFirst().get();
        Assertions.assertEquals(warehouseInfo1P1.getDelivery(), 2);
        Assertions.assertEquals(warehouseInfo1P1.getReceived(), 5);
        Assertions.assertEquals(warehouseInfo1P1.getBalance(), 3);
        WarehouseInfo warehouseInfo1P2 = warehouseInfos1.stream().filter(i -> i.getProduct().getId().equals(p2.getId())).findFirst().get();
        Assertions.assertEquals(warehouseInfo1P2.getDelivery(), 3);
        Assertions.assertEquals(warehouseInfo1P2.getReceived(), 10);
        Assertions.assertEquals(warehouseInfo1P2.getBalance(), 7);
        WarehouseInfo warehouseInfo1P3 = warehouseInfos1.stream().filter(i -> i.getProduct().getId().equals(p3.getId())).findFirst().get();
        Assertions.assertEquals(warehouseInfo1P3.getDelivery(), 0);
        Assertions.assertEquals(warehouseInfo1P3.getReceived(), 0);
        Assertions.assertEquals(warehouseInfo1P3.getBalance(), 0);

        // In a specific date
        List<WarehouseInfo> warehouseInfos2 = warehouseInfoController.getWarehouseInfos(null, null, specificDate);
        Assertions.assertEquals(warehouseInfos2.size(), products.size());
        WarehouseInfo warehouseInfo2P1 = warehouseInfos2.stream().filter(i -> i.getProduct().getId().equals(p1.getId())).findFirst().get();
        Assertions.assertEquals(warehouseInfo2P1.getDelivery(), 5);
        Assertions.assertEquals(warehouseInfo2P1.getReceived(), 20);
        Assertions.assertEquals(warehouseInfo2P1.getBalance(), 15);
        WarehouseInfo warehouseInfo2P2 = warehouseInfos2.stream().filter(i -> i.getProduct().getId().equals(p2.getId())).findFirst().get();
        Assertions.assertEquals(warehouseInfo2P2.getDelivery(), 0);
        Assertions.assertEquals(warehouseInfo2P2.getReceived(), 0);
        Assertions.assertEquals(warehouseInfo2P2.getBalance(), 0);
        WarehouseInfo warehouseInfo2P3 = warehouseInfos2.stream().filter(i -> i.getProduct().getId().equals(p3.getId())).findFirst().get();
        Assertions.assertEquals(warehouseInfo2P3.getDelivery(), 4);
        Assertions.assertEquals(warehouseInfo2P3.getReceived(), 15);
        Assertions.assertEquals(warehouseInfo2P3.getBalance(), 11);

    }
}
