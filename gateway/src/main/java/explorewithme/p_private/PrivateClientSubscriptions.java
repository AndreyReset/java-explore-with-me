package explorewithme.p_private;

import explorewithme.lib.EventsSort;
import explorewithme.lib.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrivateClientSubscriptions extends BaseClient {

    @Autowired
    public PrivateClientSubscriptions(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> getSubscriptions(long userId) {
        return get("users/" + userId + "/subscriptions");
    }

    public ResponseEntity<Object> getSubscribers(long userId) {
        return get("users/" + userId + "/subscriptions/subscribers");
    }

    public ResponseEntity<Object> getEvents(long userId, EventsSort eventsSort) {
        Map<String, Object> parameters = Map.of(
                "sort", eventsSort
        );
        return get("users/" + userId + "/subscriptions/events?sort={sort}", parameters);
    }

    public ResponseEntity<Object> createRequest(long userId, long userId2) {
        return put("users/" + userId + "/subscriptions/request/" + userId2);
    }

    public ResponseEntity<Object> confirmRequest(long userId, long userId2) {
        return put("users/" + userId + "/subscriptions/request/" + userId2 + "/confirm");
    }

    public ResponseEntity<Object> rejectRequest(long userId, long userId2) {
        return put("users/" + userId + "/subscriptions/request/" + userId2 + "/reject");
    }

    public ResponseEntity<Object> cancelRequest(long userId, long userId2) {
        return put("users/" + userId + "/subscriptions/request/" + userId2 + "/cancel");
    }
}
