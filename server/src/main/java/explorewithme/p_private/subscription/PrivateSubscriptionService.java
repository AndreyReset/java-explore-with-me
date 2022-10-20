package explorewithme.p_private.subscription;

import explorewithme.dto.EventFullDto;
import explorewithme.dto.SubscriptionRequestDto;
import explorewithme.dto.UserShortDto;
import explorewithme.lib.EventsSort;

import java.util.List;

public interface PrivateSubscriptionService {

    List<UserShortDto> getSubscriptions(long userId);

    List<UserShortDto> getSubscribers(long userId);

    List<EventFullDto> getEvents(long userId, EventsSort sort);

    SubscriptionRequestDto createRequest(long userId, long userId2);

    SubscriptionRequestDto confirmRequest(long userId, long userId2);

    SubscriptionRequestDto rejectRequest(long userId, long userId2);

    SubscriptionRequestDto cancelRequest(long userId, long userId2);
}
