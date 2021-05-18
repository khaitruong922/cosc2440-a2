package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.Product;
import s3818074_s3818487.cosc2440a2.models.ReceivingDetail;
import s3818074_s3818487.cosc2440a2.repositories.ProductRepository;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingDetailRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ReceivingDetailService extends AbstractService<ReceivingDetail, UUID> {
    private final ProductRepository productRepository;

    @Autowired
    public ReceivingDetailService(ReceivingDetailRepository repo, ProductRepository productRepository) {
        super(repo);
        this.productRepository = productRepository;
    }

    @Override
    public ReceivingDetail add(ReceivingDetail receivingDetail) {
        Optional<Product> productOptional = productRepository.findById(receivingDetail.getProduct().getId());
        if (productOptional.isEmpty()) return null;
        receivingDetail.setProduct(productOptional.get());
        return super.add(receivingDetail);
    }
}
