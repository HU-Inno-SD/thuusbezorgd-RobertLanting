package nl.hu.inno.order.domain.order;

import java.time.LocalDateTime;

public class Date {

    private LocalDateTime date;

    public Date(LocalDateTime date) {
        validateDate(date);
        this.date = date;
    }

    private void validateDate(LocalDateTime date) {
       if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }

    public LocalDateTime get() {
        return date;
    }
}
