package explorewithme.lib.util;

import java.time.LocalDateTime;

public class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static void checkStartTimeOfEvent(LocalDateTime inDateTime, long hours) {
        if (inDateTime == null) return;
        if (inDateTime.isBefore(LocalDateTime.now().plusHours(hours))) {
            throw new IllegalArgumentException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через " + hours + " ч от текущего момента");
        }
    }

    public static void checkRangeTime(LocalDateTime inStartTime, LocalDateTime inEndTime) {
        if (inStartTime != null && inEndTime != null) {
            if (inEndTime.isBefore(inStartTime)) {
                throw new IllegalArgumentException("Start time > End time");
            }
        }
    }
}
