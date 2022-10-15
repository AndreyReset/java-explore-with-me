package explorewithme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "events", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title; //заголовок

    private String annotation; //краткое описание

    private String description; //Полное описание

    @Enumerated(EnumType.STRING)
    private EventStatus status; //статус

    private int views; //кол-во просмотров

    @Column(name = "confirmed_requests")
    private int confirmedRequests; //Количество одобренных заявок на участие в данном событии

    @Column(name = "participant_limit")
    private int participantLimit; //Ограничение на количество участников. Значение 0 - означает отсутствие ограничения

    private Boolean paid; //нужно ли оплачивать участие

    @Column(name = "requester_moderation")
    private boolean requestModeration; //Нужна ли пре-модерация заявок на участие, def = True

    @Column(name = "event_date")
    private LocalDateTime eventDate; //дата и время события

    @Column(name = "created_on")
    private LocalDateTime createdOn; //дата создание события

    @Column(name = "published_on")
    private LocalDateTime publishedOn; //дата публикации

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany(mappedBy = "events", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Compilation> compilations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
