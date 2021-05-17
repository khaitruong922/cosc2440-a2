package s3818074_s3818487.cosc2440a2.filters;

import s3818074_s3818487.cosc2440a2.models.DateEntity;
import s3818074_s3818487.cosc2440a2.models.ReceivingNote;
import s3818074_s3818487.cosc2440a2.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DateFilter<T extends DateEntity> {
    private List<T> dateEntities;

    public DateFilter(List<T> dateEntities) {
        this.dateEntities = dateEntities;
    }

    public DateFilter<T> start(Date startDate) {
        if (startDate == null) return this;
        dateEntities = dateEntities.stream().filter(e -> DateUtils.isAfterInclusive(e.getDate(), startDate)).collect(Collectors.toList());
        return this;
    }

    public DateFilter<T> in(Date date) {
        if (date == null) return this;
        dateEntities = dateEntities.stream().filter(e -> DateUtils.isSameDay(e.getDate(), date)).collect(Collectors.toList());
        return this;
    }

    public DateFilter<T> end(Date endDate) {
        if (endDate == null) return this;
        dateEntities = dateEntities.stream().filter(e -> DateUtils.isBeforeInclusive(e.getDate(), endDate)).collect(Collectors.toList());
        return this;
    }

    public List<T> get() {
        return dateEntities;
    }
}
