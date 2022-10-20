package explorewithme.p_private.subscription;

import explorewithme.dto.EventFullDto;
import explorewithme.dto.SubscriptionRequestDto;
import explorewithme.dto.UserShortDto;
import explorewithme.lib.EventsSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/users/{userId}/subscriptions")
public class PrivateSubscriptionController {

    private final PrivateSubscriptionService service;

    @GetMapping
    public List<UserShortDto> getSubscriptions(@PathVariable long userId) {
        log.info("SERVER GET - получить список на кого подписан пользователь с id = {}", userId);
        return service.getSubscriptions(userId);
    }

    @GetMapping("/subscribers")
    public List<UserShortDto> getSubscribers(@PathVariable long userId) {
        log.info("SERVER GET - получить список кто подписан на пользователя с id = {}", userId);
        return service.getSubscribers(userId);
    }

    @GetMapping("/events")
    public List<EventFullDto> getEvents(@PathVariable long userId,
                                        @RequestParam EventsSort sort) {
        log.info("SERVER GET - получить список актуальных событий на кого подписан " +
                "пользователь с id = {}, сортировка по = {}", userId, sort);
        return service.getEvents(userId, sort);
    }

    @PostMapping("/request/{userId2}")
    public SubscriptionRequestDto createRequest(@PathVariable long userId,
                                                @PathVariable long userId2) {
        log.info("SERVER POST - отправить запрос на подписку пользователя userId={} к userId2={}", userId, userId2);
        return service.createRequest(userId, userId2);
    }

    @PutMapping("/request/{userId2}/confirm")
    public SubscriptionRequestDto confirmRequest(@PathVariable long userId,
                                                 @PathVariable long userId2) {
        log.info("SERVER PUT - подтверждение запроса на подписку к userId={} от userId2={}", userId, userId2);
        return service.confirmRequest(userId, userId2);
    }

    @PutMapping("/request/{userId2}/reject")
    public SubscriptionRequestDto rejectRequest(@PathVariable long userId,
                                                @PathVariable long userId2) {
        log.info("SERVER PUT - отклонение запроса на подписку к userId={} от userId2={}", userId, userId2);
        return service.rejectRequest(userId, userId2);
    }

    @PutMapping("/request/{userId2}/cancel")
    public SubscriptionRequestDto cancelRequest(@PathVariable long userId,
                                                @PathVariable long userId2) {
        log.info("SERVER PUT - отмена запроса на подписку от userId={} к userId2={}", userId, userId2);
        return service.cancelRequest(userId, userId2);
    }
}
