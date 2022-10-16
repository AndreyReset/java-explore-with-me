package explorewithme.p_admin.event;


import explorewithme.dto.EventFullDto;
import explorewithme.lib.EventsSort;
import explorewithme.lib.in.AdminUpdateEventRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getEvents(Long[] users,
                                 String[] states,
                                 Long[] categories,
                                 LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd,
                                 boolean onlyAvailable,
                                 EventsSort sort,
                                 Integer from,
                                 Integer size);

    EventFullDto updateEvent(long eventId, AdminUpdateEventRequest dto);

    EventFullDto publishEvent(long eventId);

    EventFullDto rejectEvent(long eventId);
}
