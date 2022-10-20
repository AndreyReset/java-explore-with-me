package explorewithme.p_private.subscription;

import explorewithme.dto.EventFullDto;
import explorewithme.dto.SubscriptionRequestDto;
import explorewithme.dto.UserShortDto;
import explorewithme.lib.EventsSort;
import explorewithme.lib.exception.BadRequestException;
import explorewithme.lib.exception.ObjNotFoundException;
import explorewithme.mapper.EventMapper;
import explorewithme.mapper.UserMapper;
import explorewithme.model.SubscriptionRequest;
import explorewithme.model.SubscriptionStatus;
import explorewithme.model.User;
import explorewithme.repository.EventsRepository;
import explorewithme.repository.SubscriptionRepository;
import explorewithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static explorewithme.mapper.SubscriptionMapper.toDto;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PrivateSubscriptionServiceImpl implements PrivateSubscriptionService {

    private final SubscriptionRepository repository;

    private final UserRepository userRepository;

    private final EventsRepository eventsRepository;

    @Override
    public List<UserShortDto> getSubscriptions(long userId) {
        return userRepository.findSubscriptions(userId).stream()
                .map(UserMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserShortDto> getSubscribers(long userId) {
        return userRepository.findSubscribers(userId).stream()
                .map(UserMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventFullDto> getEvents(long userId, EventsSort eventSort) {
        return eventsRepository.findActualEventsForSubscriber(userId,
                        LocalDateTime.now(),
                        Sort.by(Sort.Direction.ASC, getSort(eventSort)))
                .stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubscriptionRequestDto createRequest(long userId, long userId2) {
        User user1 = getUserById(userId);
        User user2 = getUserById(userId2);

        SubscriptionStatus status;
        if (user2.isSubscriptionFlag())
            status = SubscriptionStatus.WAITING;
        else
            status = SubscriptionStatus.CONFIRM;

        SubscriptionRequest sr = new SubscriptionRequest(user1, user2, status);
        return toDto(repository.save(sr));
    }

    @Override
    @Transactional
    public SubscriptionRequestDto confirmRequest(long userId, long userId2) {
        SubscriptionRequest sr = getSubscriptionRequest(userId, userId2);
        if (sr.getStatus().equals(SubscriptionStatus.CANCEL))
            throw new BadRequestException("Запрос отменен, обновление запрещено");
        if (sr.getStatus().equals(SubscriptionStatus.CONFIRM))
            throw new BadRequestException("Запрос уже подтвержден ранее");
        sr.setStatus(SubscriptionStatus.CONFIRM);
        return toDto(repository.save(sr));
    }

    @Override
    @Transactional
    public SubscriptionRequestDto rejectRequest(long userId, long userId2) {
        SubscriptionRequest sr = getSubscriptionRequest(userId, userId2);
        if (sr.getStatus().equals(SubscriptionStatus.CANCEL))
            throw new BadRequestException("Запрос отменен, обновление запрещено");
        if (sr.getStatus().equals(SubscriptionStatus.REJECT))
            throw new BadRequestException("Запрос отклонен ранее");
        sr.setStatus(SubscriptionStatus.REJECT);
        return toDto(repository.save(sr));
    }

    @Override
    @Transactional
    public SubscriptionRequestDto cancelRequest(long userId, long userId2) {
        SubscriptionRequest sr = getSubscriptionRequest(userId2, userId);
        if (sr.getStatus().equals(SubscriptionStatus.CANCEL))
            throw new BadRequestException("Запрос отменен ранее");
        sr.setStatus(SubscriptionStatus.CANCEL);
        return toDto(repository.save(sr));
    }

    private String getSort(EventsSort eventSort) {
        String sort = "";
        if (eventSort.equals(EventsSort.EVENT_DATE)) sort = "eventDate";
        if (eventSort.equals(EventsSort.VIEWS)) sort = "views";
        return sort;
    }

    private User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjNotFoundException("Пользователь не найден, id = " + id));
    }

    private SubscriptionRequest getSubscriptionRequest(long id1, long id2) {
        return repository.findBySubscriber_IdAndEventOwner_Id(id1, id2)
                .orElseThrow(() -> new ObjNotFoundException("Запрос не найден"));
    }
}
