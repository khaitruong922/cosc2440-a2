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
        Product product = orderDetail.getProduct();
        if (product == null) throw new RuntimeException("Missing product argument!");
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if (productOptional.isEmpty()) throw new RuntimeException("Product not found!");
        orderDetail.setProduct(productOptional.get());
        return super.add(orderDetail);
    }

    @Override
    public OrderDetail updateById(OrderDetail updatedOrderDetail, UUID id) {
        Optional<OrderDetail> orderDetailOptional = repo.findById(id);
        if (orderDetailOptional.isEmpty()) throw new RuntimeException("Order detail not found");
        OrderDetail orderDetail = orderDetailOptional.get();
        if (updatedOrderDetail.getProduct() != null) {
            Optional<Product> updatedProduct = productRepository.findById(updatedOrderDetail.getProduct().getId());
            if (updatedProduct.isEmpty()) throw new RuntimeException("There is no valid product!");
            orderDetail.setProduct(updatedProduct.orElse(orderDetail.getProduct()));
        }
        orderDetail.setPrice(Optional.ofNullable(updatedOrderDetail.getPrice()).orElse(orderDetail.getPrice()));
        orderDetail.setQuantity(Optional.ofNullable(updatedOrderDetail.getQuantity()).orElse(orderDetail.getQuantity()));
        return orderDetail;
    }
}
