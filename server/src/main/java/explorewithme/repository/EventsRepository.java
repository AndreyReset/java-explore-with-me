package explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import explorewithme.model.Event;

import java.util.List;
import java.util.Optional;


public interface EventsRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findEventsByCompilationsId(long compilationsId);

    List<Event> findEventsByInitiator_Id(long initiatorId, Pageable pageable);

    Optional<Event> findEventByIdAndInitiator_Id(long id, long initiatorId);
}
