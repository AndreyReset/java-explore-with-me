package explorewithme.mapper;

import explorewithme.dto.SubscriptionRequestDto;
import explorewithme.model.SubscriptionRequest;

public class SubscriptionMapper {

    public static SubscriptionRequestDto toDto (SubscriptionRequest request) {
        return SubscriptionRequestDto.builder()
                .subscriber(request.getSubscriber())
                .eventOwner(request.getEventOwner())
                .status(request.getStatus())
                .build();
    }
}
