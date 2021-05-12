package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.models.OrderDetail;

import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.OrderDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.OrderRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderService extends AbstractService<Order, UUID> {
    private final OrderDetailRepository orderDetailRepository;

    private final StaffRepository staffRepository;

    @Autowired
    public OrderService(OrderRepository repo, OrderDetailRepository orderDetailRepository, StaffRepository staffRepository) {
        super(repo);
        this.orderDetailRepository = orderDetailRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public Order add(Order order) {
        AtomicBoolean isRolledBack = new AtomicBoolean(false);
        List<OrderDetail> listOfOrderDetails = new ArrayList<>();
        // Handle order details
        order.getOrderDetails().forEach(od -> {
            Optional<OrderDetail> orderDetail = orderDetailRepository.findById(od.getId());
            if (orderDetail.isEmpty()) {
                isRolledBack.set(true);
            } else {
                orderDetail.get().setOrder(order);
                listOfOrderDetails.add(orderDetail.get());
            }
        });
        // Handle staff
        Optional<Staff> staff = staffRepository.findById(order.getStaff().getId());
        if (staff.isEmpty()) {
            isRolledBack.set(true);
        } else {
            System.out.println(staff.get().getName());
            order.setStaff(staff.get());
        }
        if (isRolledBack.get()) throw new Error();
        order.setOrderDetails(listOfOrderDetails);
        return super.add(order);
    }

    @Override
    public Order updateById(Order orderBody, UUID uuid) {
        Order order = repo.getOne(uuid);
        List<OrderDetail> updatedListOfOrderDetail = new ArrayList<>();
        // Handle Order Details update
        if (orderBody.getOrderDetails() != null){
            orderBody.getOrderDetails().forEach(orderDetail -> {
                Optional<OrderDetail> updatedOrderDetail = orderDetailRepository.findById(orderDetail.getId());
                if (updatedOrderDetail.isEmpty()) throw new Error();
                updatedOrderDetail.get().setOrder(orderBody);
                updatedListOfOrderDetail.add(updatedOrderDetail.get());
            });
            order.setOrderDetails(Optional.of(updatedListOfOrderDetail).orElse(order.getOrderDetails()));
        }
        // Handle Staff update
        if (orderBody.getStaff() != null){
            Optional<Staff> staff = staffRepository.findById(orderBody.getStaff().getId());
            order.setStaff(staff.orElse(order.getStaff()));
        }
        // TODO Handle Date update
//        order.setDate(Optional.of(orderBody.getDate()).orElse(order.getDate()));
        return repo.save(order);
    }
}
