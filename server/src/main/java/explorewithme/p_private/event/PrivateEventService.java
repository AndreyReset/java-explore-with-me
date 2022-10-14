package explorewithme.p_private.event;

import explorewithme.dto.EventFullDto;
import explorewithme.dto.EventShortDto;
import explorewithme.dto.ParticipationRequestDto;
import explorewithme.lib.in.NewEventDto;
import explorewithme.lib.in.UpdateEventRequest;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getEvents(long userId, Integer from, Integer size);

    EventFullDto updateEvent(long userId, UpdateEventRequest event);

    EventFullDto createEvent(long userId, NewEventDto event);

    EventFullDto getEvent(long userId, long eventId);

    EventFullDto cancelEvent(long userId, long eventId);

    List<ParticipationRequestDto> getEventRequests(long userId, long eventId);

    ParticipationRequestDto confirmEventRequest(long userId, long eventId, long reqId);

    ParticipationRequestDto rejectEventRequest(long userId, long eventId, long reqId);
}
