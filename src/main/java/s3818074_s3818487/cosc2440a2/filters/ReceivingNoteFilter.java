package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.ReceivingNote;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReceivingNoteFilter extends DateFilter<ReceivingNote> {

    public ReceivingNoteFilter(List<ReceivingNote> dateEntities) {
        super(dateEntities);
    }
}
