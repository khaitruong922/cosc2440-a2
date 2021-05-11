package s3818074_s3818487.cosc2440a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s3818074_s3818487.cosc2440a2.models.DeliveryNote;

import java.util.UUID;

public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, UUID> {
}
