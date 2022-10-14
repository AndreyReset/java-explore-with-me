package explorewithme.p_admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import explorewithme.lib.EventsSort;
import explorewithme.lib.client.BaseClient;
import explorewithme.lib.in.AdminUpdateEventRequest;

import java.util.Map;
import java.util.Optional;

import static explorewithme.lib.util.DataToLine.arrToLine;
import static explorewithme.lib.util.DataToLine.varToLine;


@Service
public class AdminClientEvents extends BaseClient {

    @Autowired
    public AdminClientEvents(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> getEvents(Optional<Long[]> users,
                                            Optional<String[]> states,
                                            Optional<Long[]> categories,
                                            Optional<String> rangeStart,
                                            Optional<String> rangeEnd,
                                            boolean onlyAvailable,
                                            EventsSort sort,
                                            Integer from,
                                            Integer size) {
        Map<String, Object> parameters = Map.of(
                "users", arrToLine(users),
                "states", arrToLine(states),
                "categories", arrToLine(categories),
                "rangeStart", varToLine(rangeStart),
                "rangeEnd", varToLine(rangeEnd),
                "onlyAvailable", onlyAvailable,
                "sort", sort,
                "from", from,
                "size", size
        );
        return get("admin/events?users={users}&&states={states}&&categories={categories}" +
                "&&rangeStart={rangeStart}&&rangeEnd={rangeEnd}&&onlyAvailable={onlyAvailable}" +
                "&&sort={sort}&&from={from}&&size={size}", parameters);
    }

    public ResponseEntity<Object> updateEvent(long eventId, AdminUpdateEventRequest dto) {
        return put("admin/events/" + eventId, dto);
    }

    public ResponseEntity<Object> publishEvent(long eventId) {
        return patch("admin/events/" + eventId + "/publish");
    }

    public ResponseEntity<Object> rejectEvent(long eventId) {
        return patch("admin/events/" + eventId + "/reject");
    }
}
