package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Order;
import s3818074_s3818487.cosc2440a2.models.OrderDetail;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.models.Staff;
import s3818074_s3818487.cosc2440a2.repositories.OrderDetailRepository;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
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
        if(product.isEmpty()) return null;
        orderDetail.setProduct(product.get());
        return super.add(orderDetail);
    }

    @Override
    public HttpStatus updateById(OrderDetail orderDetail, UUID id) {
        try {
            OrderDetail t = repo.getOne(id);
            if (orderDetail.getProduct() != null){
                Optional<Product> updatedProduct = productRepository.findById(orderDetail.getProduct().getId());
                if (updatedProduct.isEmpty()) throw new Error("There is no valid product");
                t.setProduct(updatedProduct.orElse(t.getProduct()));
            }
            t.setPrice(Optional.of(orderDetail.getPrice()).orElse(t.getPrice()));
            t.setQuantity(Optional.of(orderDetail.getQuantity()).orElse(t.getQuantity()));
            repo.save(t);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
