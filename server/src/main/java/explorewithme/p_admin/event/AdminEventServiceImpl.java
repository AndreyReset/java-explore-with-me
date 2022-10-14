package explorewithme.p_admin.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import explorewithme.dto.EventFullDto;
import explorewithme.lib.EventsSort;
import explorewithme.lib.exception.BadRequestException;
import explorewithme.lib.exception.ObjNotFoundException;
import explorewithme.lib.in.AdminUpdateEventRequest;
import explorewithme.mapper.EventMapper;
import explorewithme.model.Category;
import explorewithme.model.Event;
import explorewithme.model.EventStatus;
import explorewithme.pageable.OffsetLimitPageable;
import explorewithme.repository.CategoryRepository;
import explorewithme.repository.EventsRepository;

import java.util.List;
import java.util.stream.Collectors;

import static explorewithme.lib.util.ValidDateTime.getDateTime;
import static explorewithme.mapper.EventMapper.toFullDto;
import static explorewithme.model.EventStatus.eventsStatus;
import static explorewithme.p_public.SpecificationForFind.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminEventServiceImpl implements AdminEventService {

    private final EventsRepository repository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<EventFullDto> getEvents(Long[] users,
                                        String[] states,
                                        Long[] categories,
                                        String rangeStart,
                                        String rangeEnd,
                                        boolean onlyAvailable,
                                        EventsSort eventSort,
                                        Integer from,
                                        Integer size) {
        String sort = "";
        if (eventSort.equals(EventsSort.EVENT_DATE)) sort = "eventDate";
        if (eventSort.equals(EventsSort.VIEWS)) sort = "views";
        Pageable pageable = OffsetLimitPageable.of(from, size, Sort.by(Sort.Direction.ASC, sort));
        return repository.findAll(
                usersFilter(users)
                .and(statusFilter(eventsStatus(states)))
                .and(categoriesFilter(categories))
                .and(rangeTimeFilter(rangeStart, rangeEnd)),
                pageable
        ).toList().stream().map(EventMapper::toFullDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long eventId, AdminUpdateEventRequest dto) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        if (dto.getAnnotation() != null)
            event.setAnnotation(dto.getAnnotation());
        if (dto.getCategory() != null && dto.getCategory() != 0) {
            Category category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new ObjNotFoundException("Категория не найдена"));
            event.setCategory(category);
        }
        if (dto.getDescription() != null)
            event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null)
            event.setEventDate(getDateTime(dto.getEventDate()));
        if (dto.getLocation() != null) {
            event.getLocation().setLon(dto.getLocation().getLon());
            event.getLocation().setLat(dto.getLocation().getLat());
        }
        if (dto.getPaid() != null)
            event.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null)
            event.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getRequestModeration() != null)
            event.setRequestModeration(dto.getRequestModeration());

        return toFullDto(repository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        if (!event.getStatus().equals(EventStatus.PENDING)) throw new BadRequestException("Статус не PENDING");
        event.setStatus(EventStatus.PUBLISHED);
        return toFullDto(repository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        if (event.getStatus().equals(EventStatus.PUBLISHED)) throw new BadRequestException("Статус PUBLISHED");
        event.setStatus(EventStatus.CANCELED);
        return toFullDto(repository.save(event));
    }
}
