package explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import explorewithme.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
