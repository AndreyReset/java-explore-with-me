package explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import explorewithme.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIn(List<Long> id);

    @Query("SELECT u " +
            "FROM User AS u " +
            "JOIN SubscriptionRequest AS sr ON sr.eventOwner.id=u.id " +
            "WHERE sr.subscriber.id=?1 ")
    List<User> findSubscriptions(long id);

    @Query("SELECT u " +
            "FROM User AS u " +
            "JOIN SubscriptionRequest AS sr ON sr.subscriber.id=u.id " +
            "WHERE sr.eventOwner.id=?1 ")
    List<User> findSubscribers(long id);
}
