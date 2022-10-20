package explorewithme.p_private;

import explorewithme.lib.EventsSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/users/{userId}/subscriptions")
public class PrivateControllerSubscriptions {

    private final PrivateClientSubscriptions client;

    @GetMapping
    public ResponseEntity<Object> getSubscriptions(@PathVariable long userId) {
        log.info("GET - получить список на кого подписан пользователь с id = {}", userId);
        return client.getSubscriptions(userId);
    }

    @GetMapping("/subscribers")
    public ResponseEntity<Object> getSubscribers(@PathVariable long userId) {
        log.info("GET - получить список кто подписан на пользователя с id = {}", userId);
        return client.getSubscribers(userId);
    }

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(@PathVariable long userId,
                                            @RequestParam(defaultValue = "EVENT_DATE") String sort) {
        log.info("GET - получить список актуальных событий на кого подписан " +
                "пользователь с id = {}, сортировка по = {}", userId, sort);
        EventsSort eventsSort = EventsSort.from(sort)
                .orElseThrow(() -> new IllegalArgumentException("Unknown sort: " + sort));
        return client.getEvents(userId, eventsSort);
    }

    @PostMapping("/request/{userId2}")
    public ResponseEntity<Object> createRequest(@PathVariable long userId,
                                                @PathVariable long userId2) {
        log.info("POST - отправить запрос на подписку пользователя userId={} к userId2={}", userId, userId2);
        return client.createRequest(userId, userId2);
    }

    @PutMapping("/request/{userId2}/confirm")
    public ResponseEntity<Object> confirmRequest(@PathVariable long userId,
                                                 @PathVariable long userId2) {
        log.info("PUT - подтверждение запроса на подписку к userId={} от userId2={}", userId, userId2);
        return client.confirmRequest(userId, userId2);
    }

    @PutMapping("/request/{userId2}/reject")
    public ResponseEntity<Object> rejectRequest(@PathVariable long userId,
                                                 @PathVariable long userId2) {
        log.info("PUT - отклонение запроса на подписку к userId={} от userId2={}", userId, userId2);
        return client.rejectRequest(userId, userId2);
    }

    @PutMapping("/request/{userId2}/cancel")
    public ResponseEntity<Object> cancelRequest(@PathVariable long userId,
                                                @PathVariable long userId2) {
        log.info("PUT - отмена запроса на подписку от userId={} к userId2={}", userId, userId2);
        return client.cancelRequest(userId, userId2);
    }
}
