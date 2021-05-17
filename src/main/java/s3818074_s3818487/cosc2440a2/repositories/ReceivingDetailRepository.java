package s3818074_s3818487.cosc2440a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import s3818074_s3818487.cosc2440a2.models.ReceivingDetail;

import java.util.UUID;

public interface ReceivingDetailRepository extends JpaRepository<ReceivingDetail, UUID> {
}
