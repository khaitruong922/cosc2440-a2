package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.DeliveryNote;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryNoteFilter {
    private List<DeliveryNote> deliveryNotes;

    public DeliveryNoteFilter(List<DeliveryNote> deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }

    public DeliveryNoteFilter start(Date startDate) {
        if (startDate == null) return this;
        deliveryNotes = deliveryNotes.stream().filter(i -> DateUtils.isAfterInclusive(i.getDate(), startDate)).collect(Collectors.toList());
        return this;
    }

    public DeliveryNoteFilter in(Date date) {
        if (date == null) return this;
        deliveryNotes = deliveryNotes.stream().filter(i -> DateUtils.isSameDay(i.getDate(), date)).collect(Collectors.toList());
        return this;
    }

    public DeliveryNoteFilter end(Date endDate) {
        if (endDate == null) return this;
        deliveryNotes = deliveryNotes.stream().filter(i -> DateUtils.isBeforeInclusive(i.getDate(), endDate)).collect(Collectors.toList());
        return this;
    }

    public List<DeliveryNote> get() {
        return deliveryNotes;
    }
}
