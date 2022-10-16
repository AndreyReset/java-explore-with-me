package explorewithme.lib.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminUpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}
