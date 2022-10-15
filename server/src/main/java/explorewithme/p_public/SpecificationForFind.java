package explorewithme.p_public;

import org.springframework.data.jpa.domain.Specification;
import explorewithme.model.Compilation;
import explorewithme.model.Event;
import explorewithme.model.EventStatus;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecificationForFind {

    public static Specification<Event> statusFilter(EventStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Event> statusFilter(List<EventStatus> status) {
        return (root, query, criteriaBuilder) -> {
            if (status.isEmpty()) return null;
            List<Predicate> predicates = new ArrayList<>();
            for (EventStatus st : status) {
                predicates.add(criteriaBuilder.equal(root.get("status"), st));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> usersFilter(Long[] ids) {
        return (root, query, criteriaBuilder) -> {
            if (ids.length == 0) return null;
            List<Predicate> predicates = new ArrayList<>();
            for (Long id : ids) {
                predicates.add(criteriaBuilder.equal(root.get("initiator"), id));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> categoriesFilter(Long[] ids) {
        return (root, query, criteriaBuilder) -> {
            if (ids.length == 0) return null;
            List<Predicate> predicates = new ArrayList<>();
            for (Long id : ids) {
                predicates.add(criteriaBuilder.equal(root.get("category"), id));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> rangeTimeFilter(LocalDateTime start, LocalDateTime end) {
        return (root, query, criteriaBuilder) -> {
            LocalDateTime startTime = start;
            if (startTime == null || end == null) {
                startTime = LocalDateTime.now();
                return criteriaBuilder.greaterThan(root.get("eventDate"), startTime);
            }
            return criteriaBuilder.between(root.get("eventDate"), start, end);
        };
    }

    public static Specification<Event> textFilter(String text) {
        String finalText = text.toLowerCase();
        return (root, query, criteriaBuilder) -> {
            if (finalText.isEmpty()) return null;
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
                    "%" + finalText + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + finalText + "%"));
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> paidFilter(Optional<Boolean> paid) {
        if (paid.isEmpty()) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paid"), paid.get());
    }

    public static Specification<Compilation> pinnedFilter(Optional<Boolean> pinned) {
        if (pinned.isEmpty()) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("pinned"), pinned.get());
    }

    public static Specification<Event> availableFilter(Boolean onlyAvailable) {
        if (onlyAvailable == null) return null;
        return (root, query, criteriaBuilder) -> {
            if (onlyAvailable)
                return criteriaBuilder.lessThan(root.get("confirmedRequests"), root.get("participantLimit"));
            return null;
        };
    }
}
