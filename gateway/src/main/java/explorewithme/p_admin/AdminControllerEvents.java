package explorewithme.p_admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import explorewithme.lib.EventsSort;
import explorewithme.lib.in.AdminUpdateEventRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static explorewithme.lib.util.DateTimeUtils.checkRangeTime;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/admin/events")
public class AdminControllerEvents {

    private final AdminClientEvents clientEvents;

    @GetMapping
    public ResponseEntity<Object> getEvents(@RequestParam(required = false) Long[] users,
                                            @RequestParam(required = false) String[] states,
                                            @RequestParam(required = false) Long[] categories,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                            @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                            @Positive @RequestParam(defaultValue = "10") Integer size) {
        EventsSort eventsSort = EventsSort.from(sort)
                .orElseThrow(() -> new IllegalArgumentException("Unknown sort: " + sort));
        checkRangeTime(rangeStart, rangeEnd);
        log.info("GET events");
        return clientEvents.getEvents(users, states, categories, rangeStart, rangeEnd,
                onlyAvailable, eventsSort, from, size);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(@PathVariable long eventId,
                                              @RequestBody AdminUpdateEventRequest dto) {
        return clientEvents.updateEvent(eventId, dto);
    }

    @PatchMapping("/{eventId}/publish")
    public ResponseEntity<Object> publishEvent(@PathVariable long eventId) {
        return clientEvents.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public ResponseEntity<Object> rejectEvent(@PathVariable long eventId) {
        return clientEvents.rejectEvent(eventId);
    }
}
