package s3818074_s3818487.cosc2440a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3818074_s3818487.cosc2440a2.models.ReceivingDetail;
import s3818074_s3818487.cosc2440a2.repositories.ReceivingDetailRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class ReceivingDetailService extends AbstractService<ReceivingDetail, UUID> {
    @Autowired
    public ReceivingDetailService(ReceivingDetailRepository repo) {
        super(repo);
    }
}
