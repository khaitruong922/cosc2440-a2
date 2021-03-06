package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.*;
import s3818074_s3818487.cosc2440a2.repositories.*;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class OrderService extends AbstractService<Order, UUID> {
    private final OrderDetailRepository orderDetailRepository;

    private final StaffRepository staffRepository;

    private final ProviderRepository providerRepository;

    private final ReceivingNoteRepository receivingNoteRepository;
    private final ReceivingDetailRepository receivingDetailRepository;

    @Autowired
    public OrderService(OrderRepository repo, OrderDetailRepository orderDetailRepository,
                        StaffRepository staffRepository, ProviderRepository providerRepository,
                        ReceivingNoteRepository receivingNoteRepository, ReceivingDetailRepository receivingDetailRepository) {
        super(repo);
        this.orderDetailRepository = orderDetailRepository;
        this.staffRepository = staffRepository;
        this.providerRepository = providerRepository;
        this.receivingNoteRepository = receivingNoteRepository;
        this.receivingDetailRepository = receivingDetailRepository;
    }

    @Override
    public Order add(Order order) {
        // Handle staff
        Staff staff = order.getStaff();
        if (staff == null) throw new RuntimeException("Missing staff argument!");
        Optional<Staff> staffOptional = staffRepository.findById(order.getStaff().getId());
        if (staffOptional.isEmpty()) throw new RuntimeException("Staff not found!");

        // Handle provider
        Provider provider = order.getProvider();
        if (provider == null) throw new RuntimeException("Missing provider argument");
        Optional<Provider> providerOptional = providerRepository.findById(order.getProvider().getId());
        if (providerOptional.isEmpty()) throw new RuntimeException("Provider not found");

        // Handle order details
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (order.getOrderDetails() == null) order.setOrderDetails(Collections.emptyList());
        order.getOrderDetails().forEach(od -> {
                    Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(od.getId());
                    if (orderDetailOptional.isEmpty()) throw new RuntimeException("Order detail not found!");
                    OrderDetail orderDetail = orderDetailOptional.get();
//                    if (orderDetail.getOrder() != null)
//                        throw new RuntimeException("Order detail " + orderDetail.getId() + " has been used!");
                    orderDetail.setOrder(order);
                    orderDetails.add(orderDetail);
                }
        );
        order.setOrderDetails(orderDetails);
        return super.add(order);
    }

    @Override
    public Order updateById(Order updatedOrder, UUID id) {
        Optional<Order> orderOptional = repo.findById(id);
        if (orderOptional.isEmpty()) throw new RuntimeException("Order not found!");
        Order order = orderOptional.get();

        // Handle order details update
        if (updatedOrder.getOrderDetails() != null) {
            List<OrderDetail> orderDetails = new ArrayList<>();
            updatedOrder.getOrderDetails().forEach(od -> {
                // Set the order of all current details to null
                order.getOrderDetails().forEach(d -> d.setOrder(null));

                Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(od.getId());
                if (orderDetailOptional.isEmpty()) throw new RuntimeException("Order detail not found");
                OrderDetail orderDetail = orderDetailOptional.get();
                // Check if the order detail is null
                if (orderDetail.getOrder() != null)
                    throw new RuntimeException("Order detail " + orderDetail.getId() + " has been used!");

                orderDetail.setOrder(order);
                orderDetails.add(orderDetail);
            });
            order.setOrderDetails(orderDetails);
        }

        // Handle staff update
        if (updatedOrder.getStaff() != null) {
            Optional<Staff> staffOptional = staffRepository.findById(updatedOrder.getStaff().getId());
            order.setStaff(staffOptional.orElse(order.getStaff()));
        }
        // Handle provider update
        if (updatedOrder.getProvider() != null) {
            Optional<Provider> providerOptional = providerRepository.findById(updatedOrder.getProvider().getId());
            order.setProvider(providerOptional.orElse(order.getProvider()));
        }
        // Handle date update
        order.setDate(Optional.ofNullable(updatedOrder.getDate()).orElse(order.getDate()));

        return order;
    }

    public ReceivingNote createReceivingNote(UUID id) {
        Optional<Order> orderOptional = repo.findById(id);
        if (orderOptional.isEmpty()) throw new RuntimeException("Order not found!");
        Order order = orderOptional.get();
        List<OrderDetail> orderDetails = order.getOrderDetails() != null ? order.getOrderDetails() : new ArrayList<>();
        List<ReceivingDetail> receivingDetails = new ArrayList<>();
        ReceivingNote receivingNote = new ReceivingNote(null, order.getDate(), order.getStaff(), receivingDetails);
        orderDetails.forEach(od -> {
                    ReceivingDetail receivingDetail = new ReceivingDetail(null, od.getProduct(), od.getQuantity());
                    receivingDetail = receivingDetailRepository.saveAndFlush(receivingDetail);
                    receivingDetail = receivingDetailRepository.findById(receivingDetail.getId()).get();
                    receivingDetail.setReceivingNote(receivingNote);
                    receivingDetails.add(receivingDetail);
                }

        );
        receivingNote.setReceivingDetails(receivingDetails);
        return receivingNoteRepository.save(receivingNote);
    }
}
