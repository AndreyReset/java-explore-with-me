package explorewithme.dto;

import explorewithme.model.SubscriptionStatus;
import explorewithme.model.User;
import lombok.*;

@Data
@Builder
public class SubscriptionRequestDto {
    private User subscriber;
    private User eventOwner;
    private SubscriptionStatus status;
}
