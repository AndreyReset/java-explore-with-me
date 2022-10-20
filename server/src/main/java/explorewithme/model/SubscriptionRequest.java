package explorewithme.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subscriptions", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_subscriber")
    private User subscriber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_event_owner")
    private User eventOwner;

    private SubscriptionStatus status;

    public SubscriptionRequest(User user1, User user2, SubscriptionStatus status) {
        this.subscriber = user1;
        this.eventOwner = user2;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionRequest that = (SubscriptionRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
