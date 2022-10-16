package explorewithme.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
