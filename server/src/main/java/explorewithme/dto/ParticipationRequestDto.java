package explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import explorewithme.model.ParticipationStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipationRequestDto {
    private long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private long event;
    private long requester;
    private ParticipationStatus status;
}
