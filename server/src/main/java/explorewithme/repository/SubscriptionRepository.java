package explorewithme.repository;

import explorewithme.model.SubscriptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionRequest, Long> {

    Optional<SubscriptionRequest> findBySubscriber_IdAndEventOwner_Id(long userId1, long userid2);
}
