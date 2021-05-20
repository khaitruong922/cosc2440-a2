package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.models.OrderDetail;
import s3818074_s3818487.cosc2440a2.models.Provider;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.OrderDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.OrderRepository;
import s3818074_s3818487.cosc2440a2.repositories.ProviderRepository;
import s3818074_s3818487.cosc2440a2.repositories.StaffRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class OrderService extends AbstractService<Order, UUID> {
    private final OrderDetailRepository orderDetailRepository;

    private final StaffRepository staffRepository;

    private final ProviderRepository providerRepository;

    @Autowired
    public OrderService(OrderRepository repo, OrderDetailRepository orderDetailRepository,
                        StaffRepository staffRepository, ProviderRepository providerRepository) {
        super(repo);
        this.orderDetailRepository = orderDetailRepository;
        this.staffRepository = staffRepository;
        this.providerRepository = providerRepository;
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
                    if (orderDetail.getOrder() != null)
                        throw new RuntimeException("Order detail " + orderDetail.getId() + " has been used!");
                    orderDetail.setOrder(order);
                    orderDetails.add(orderDetail);
                }
        );
        order.setOrderDetails(orderDetails);
        return super.add(order);
    }

    @Override
    public Order updateById(Order orderBody, UUID uuid) {
        Order order = repo.getOne(uuid);
        List<OrderDetail> updatedListOfOrderDetail = new ArrayList<>();
        // Handle Order Details update
        if (orderBody.getOrderDetails() != null) {
            if (orderBody.getOrderDetails().size() > 0) {
                orderBody.getOrderDetails().forEach(orderDetail -> {
                    Optional<OrderDetail> updatedOrderDetail = orderDetailRepository.findById(orderDetail.getId());
                    if (updatedOrderDetail.isEmpty()) throw new Error();
                    if (updatedOrderDetail.get().getOrder() != null) {
                        throw new Error("Order detail " + updatedOrderDetail.get().getId() + " has been used!");
                    }
                    updatedOrderDetail.get().setOrder(order);
                    updatedListOfOrderDetail.add(updatedOrderDetail.get());
                });
            } else {
                order.getOrderDetails().forEach(orderDetail -> {
                    Optional<OrderDetail> updatedOrderDetail = orderDetailRepository.findById(orderDetail.getId());
                    if (updatedOrderDetail.isEmpty()) throw new Error();
                    if (updatedOrderDetail.get().getOrder() != null) {
                        updatedOrderDetail.get().setOrder(null);
                    }
                    updatedListOfOrderDetail.add(updatedOrderDetail.get());
                });
            }
            order.setOrderDetails(Optional.of(updatedListOfOrderDetail).orElse(order.getOrderDetails()));
        }
        // Handle Staff update
        if (orderBody.getStaff() != null) {
            Optional<Staff> staff = staffRepository.findById(orderBody.getStaff().getId());
            order.setStaff(staff.orElse(order.getStaff()));
        }
        // Handle Provider update
        if (orderBody.getProvider() != null){
            Optional<Provider> provider = providerRepository.findById(orderBody.getProvider().getId());
            order.setProvider(provider.orElse(order.getProvider()));
        }
        // Handle Date update
        order.setDate(Optional.of(orderBody.getDate()).orElse(order.getDate()));
        return repo.save(order);
    }
}
