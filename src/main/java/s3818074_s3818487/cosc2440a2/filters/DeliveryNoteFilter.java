package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.DeliveryNote;

import java.util.List;

public class DeliveryNoteFilter extends DateFilter<DeliveryNote> {
    public DeliveryNoteFilter(List<DeliveryNote> dateEntities) {
        super(dateEntities);
    }
}
