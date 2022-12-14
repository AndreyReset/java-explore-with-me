package explorewithme.p_private.request;

import explorewithme.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {

    List<ParticipationRequestDto> getRequests(long userId);

    ParticipationRequestDto createRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
