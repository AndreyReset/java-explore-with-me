package explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import explorewithme.model.Participation;
import explorewithme.model.ParticipationStatus;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByRequester_IdAndEvent_Id(long requesterId, long eventId);

    List<Participation> findByStatusAndId(ParticipationStatus status, long id);

    List<Participation> findByRequester_Id(long requesterId);

    List<Participation> findByEvent_idAndEvent_Initiator_Id(long eventId, long eventInitiatorId);

    Optional<Participation> findByIdAndEvent_IdAndEvent_Initiator_Id(long id, long eventId, long eventInitiatorId);

    Optional<Participation> findByIdAndRequester_id(long id, long requesterId);
}
