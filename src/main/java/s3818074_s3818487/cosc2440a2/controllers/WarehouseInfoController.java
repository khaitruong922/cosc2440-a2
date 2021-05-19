package s3818074_s3818487.cosc2440a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import s3818074_s3818487.cosc2440a2.filters.DeliveryNoteFilter;
import s3818074_s3818487.cosc2440a2.filters.ReceivingNoteFilter;
import s3818074_s3818487.cosc2440a2.models.DeliveryNote;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.models.WarehouseInfo;
import s3818074_s3818487.cosc2440a2.services.DeliveryNoteService;
import s3818074_s3818487.cosc2440a2.services.ReceivingNoteService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/warehouse")
public class WarehouseInfoController {
    private final ReceivingNoteService receivingNoteService;
    private final DeliveryNoteService deliveryNoteService;

    @Autowired
    public WarehouseInfoController(ReceivingNoteService receivingNoteService, DeliveryNoteService deliveryNoteService) {
        this.receivingNoteService = receivingNoteService;
        this.deliveryNoteService = deliveryNoteService;
    }

    @GetMapping
    public List<WarehouseInfo> getWarehouseInfos(@RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                 @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                 @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<ReceivingNote> receivingNotes = new ReceivingNoteFilter(receivingNoteService.getAll()).start(startDate).end(endDate).in(date).get();
        List<DeliveryNote> deliveryNotes = new DeliveryNoteFilter(deliveryNoteService.getAll()).start(startDate).end(endDate).in(date).get();
        Map<Product, Integer> receivedMap = new HashMap<>();
        Map<Product, Integer> deliveryMap = new HashMap<>();
        Set<Product> products = new LinkedHashSet<>();

        receivingNotes.forEach(rn -> {
            rn.getReceivingDetails().forEach(rd -> {
                Product product = rd.getProduct();
                receivedMap.put(product, receivedMap.getOrDefault(product, 0) + rd.getQuantity());
            });
        });
        deliveryNotes.forEach(dn -> {
            dn.getDeliveryDetails().forEach(dd -> {
                Product product = dd.getProduct();
                deliveryMap.put(product, deliveryMap.getOrDefault(product, 0) + dd.getQuantity());
            });
        });
        products.addAll(receivedMap.keySet());
        products.addAll(deliveryMap.keySet());
        return products.stream().map(p -> new WarehouseInfo(p, receivedMap.getOrDefault(p, 0), deliveryMap.getOrDefault(p, 0))).collect(Collectors.toList());
    }
}
