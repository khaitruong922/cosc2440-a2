package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.DeliveryNote;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReceivingNoteFilter {
    private List<ReceivingNote> receivingNotes;

    public ReceivingNoteFilter(List<ReceivingNote> receivingNotes) {
        this.receivingNotes = receivingNotes;
    }

    public ReceivingNoteFilter start(Date startDate) {
        if (startDate == null) return this;
        receivingNotes = receivingNotes.stream().filter(i -> DateUtils.isAfterInclusive(i.getDate(), startDate)).collect(Collectors.toList());
        return this;
    }

    public ReceivingNoteFilter in(Date date) {
        if (date == null) return this;
        receivingNotes = receivingNotes.stream().filter(i -> DateUtils.isSameDay(i.getDate(), date)).collect(Collectors.toList());
        return this;
    }

    public ReceivingNoteFilter end(Date endDate) {
        if (endDate == null) return this;
        receivingNotes = receivingNotes.stream().filter(i -> DateUtils.isBeforeInclusive(i.getDate(), endDate)).collect(Collectors.toList());
        return this;
    }

    public List<ReceivingNote> get() {
        return receivingNotes;
    }
}
