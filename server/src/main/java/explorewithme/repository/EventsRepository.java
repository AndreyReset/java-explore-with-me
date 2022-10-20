package explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import explorewithme.model.Event;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface EventsRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findEventsByCompilationsId(long compilationsId);

    List<Event> findEventsByInitiator_Id(long initiatorId, Pageable pageable);

    Optional<Event> findEventByIdAndInitiator_Id(long id, long initiatorId);

    @Query("SELECT e " +
            "FROM Event AS e " +
            "JOIN User AS u ON e.initiator.id=u.id " +
            "JOIN SubscriptionRequest AS sr ON sr.eventOwner.id=u.id " +
            "WHERE sr.subscriber.id=?1 " +
            "AND e.eventDate>?2 ")
    List<Event> findActualEventsForSubscriber(long idSubscriber, LocalDateTime time, Sort sort);
}
