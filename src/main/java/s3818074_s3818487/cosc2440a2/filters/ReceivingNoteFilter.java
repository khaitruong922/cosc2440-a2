package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.ReceivingNote;

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
        receivingNotes = receivingNotes.stream().filter(note -> note.getDate().after(startDate)).collect(Collectors.toList());
        return this;
    }

    public ReceivingNoteFilter end(Date endDate) {
        if (endDate == null) return this;
        receivingNotes = receivingNotes.stream().filter(note -> note.getDate().before(endDate)).collect(Collectors.toList());
        return this;
    }

    public List<ReceivingNote> get() {
        return receivingNotes;
    }
}
