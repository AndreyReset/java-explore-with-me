package explorewithme.p_admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import explorewithme.lib.EventsSort;
import explorewithme.lib.client.BaseClient;
import explorewithme.lib.in.AdminUpdateEventRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static explorewithme.lib.util.DataToLine.arrToLine;


@Service
public class AdminClientEvents extends BaseClient {

    private final String pattern;

    @Autowired
    public AdminClientEvents(@Value("${main-server.url}") String serverUrl,
                             RestTemplateBuilder builder,
                             @Value("${dateTimeFormat}") String pattern) {
        super(serverUrl, builder, "/");
        this.pattern = pattern;
    }

    public ResponseEntity<Object> getEvents(Long[] users,
                                            String[] states,
                                            Long[] categories,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            boolean onlyAvailable,
                                            EventsSort sort,
                                            Integer from,
                                            Integer size) {
        Map<String, Object> parameters = Map.of(
                "users", arrToLine(users),
                "states", arrToLine(states),
                "categories", arrToLine(categories),
                "rangeStart", rangeStart.format(DateTimeFormatter.ofPattern(pattern)),
                "rangeEnd", rangeEnd.format(DateTimeFormatter.ofPattern(pattern)),
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
