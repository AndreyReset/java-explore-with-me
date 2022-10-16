package explorewithme.p_pablic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import explorewithme.lib.EventsSort;
import explorewithme.lib.client.BaseClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static explorewithme.lib.util.DataToLine.arrToLine;
import static explorewithme.lib.util.DataToLine.varToLine;

@Service
public class PublicClient extends BaseClient {

    private final String pattern;

    @Autowired
    public PublicClient(@Value("${main-server.url}") String serverUrl,
                        RestTemplateBuilder builder,
                        @Value("${dateTimeFormat}") String pattern) {
        super(serverUrl, builder, "/");
        this.pattern = pattern;
    }

    public ResponseEntity<Object> getEvents(String text,
                                            String[] categories,
                                            Boolean paid,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            Boolean onlyAvailable,
                                            EventsSort eventsSort,
                                            Integer from,
                                            Integer size,
                                            HttpServletRequest oldRequest) {
        Map<String, Object> parameters = Map.of(
                "text", varToLine(text),
                "categories", arrToLine(categories),
                "paid", varToLine(paid),
                "rangeStart", rangeStart.format(DateTimeFormatter.ofPattern(pattern)),
                "rangeEnd", rangeEnd.format(DateTimeFormatter.ofPattern(pattern)),
                "onlyAvailable", onlyAvailable,
                "eventsSort", eventsSort,
                "from", from,
                "size", size
        );

        return get("events?text={text}&categories={categories}&paid={paid}" +
                "&rangeStart={rangeStart}&rangeEnd={rangeEnd}" +
                "&onlyAvailable={onlyAvailable}&eventsSort={eventsSort}&from={from}&size={size}", oldRequest, parameters);
    }

    public ResponseEntity<Object> getEvent(long id, HttpServletRequest oldRequest) {
        return get("events/" + id, oldRequest);
    }

    public ResponseEntity<Object> getCompilations(Boolean pinned, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "pinned", varToLine(pinned),
                "from", from,
                "size", size
        );
        return get("compilations?pinned={pinned}&from={from}&size={size}", parameters);
    }

    public ResponseEntity<Object> getCompilation(long compId) {
        return get("compilations/" + compId);
    }

    public ResponseEntity<Object> getCategories(Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("categories?from={from}&size={size}", parameters);
    }

    public ResponseEntity<Object> getCategory(long catId) {
        return get("categories/" + catId);
    }
}
