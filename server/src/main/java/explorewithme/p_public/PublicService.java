package explorewithme.p_public;

import explorewithme.dto.CategoryDto;
import explorewithme.dto.CompilationDto;
import explorewithme.dto.EventFullDto;
import explorewithme.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface PublicService {

    List<EventShortDto> getEvents(String text,
                                  Long[] categories,
                                  Optional<Boolean> paid,
                                  String rangeStart,
                                  String rangeEnd,
                                  Boolean onlyAvailable,
                                  String eventsSort,
                                  Integer from,
                                  Integer size,
                                  HttpServletRequest request);

    EventFullDto getEvent(long eventId, HttpServletRequest request);

    List<CompilationDto> getCompilations(Optional<Boolean> pinned, Integer from, Integer size);

    CompilationDto getCompilation(long compId);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(long catId);
}
