package explorewithme.lib.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    //список стектрейсов или описания ошибок
    private final StackTraceElement[] errors;

    //сообщение об ошике
    private final String message;

    //общее описание причины ошибки
    private final String reason;

    //код статуса HTTP ответа
    private final String status;

    //дата и время когда произошла ошибка
    private final LocalDateTime timestamp;
}