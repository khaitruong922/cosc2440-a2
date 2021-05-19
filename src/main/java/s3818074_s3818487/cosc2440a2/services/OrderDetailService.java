package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.OrderDetail;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.repositories.OrderDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderDetailService extends AbstractService<OrderDetail, UUID> {
    private final ProductRepository productRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
        super(orderDetailRepository);
        this.productRepository = productRepository;
    }

    @Override
    public OrderDetail add(OrderDetail orderDetail) {
        Optional<Product> product = productRepository.findById(orderDetail.getProduct().getId());
        if (product.isEmpty()) throw new RuntimeException("Product not found!");
        orderDetail.setProduct(product.get());
        return super.add(orderDetail);
    }

    @Override
    public OrderDetail updateById(OrderDetail newOrderDetail, UUID id) {
        Optional<OrderDetail> orderDetailOptional = repo.findById(id);
        if (orderDetailOptional.isEmpty()) return null;
        OrderDetail orderDetail = orderDetailOptional.get();
        if (newOrderDetail.getProduct() != null) {
            Optional<Product> updatedProduct = productRepository.findById(newOrderDetail.getProduct().getId());
            if (updatedProduct.isEmpty()) throw new RuntimeException("There is no valid product!");
            orderDetail.setProduct(updatedProduct.orElse(orderDetail.getProduct()));
        }
        orderDetail.setPrice(Optional.of(newOrderDetail.getPrice()).orElse(orderDetail.getPrice()));
        orderDetail.setQuantity(Optional.of(newOrderDetail.getQuantity()).orElse(orderDetail.getQuantity()));
        return repo.save(orderDetail);
    }
}
