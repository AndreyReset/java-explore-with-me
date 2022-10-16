package explorewithme.mapper;

import explorewithme.dto.CompilationDto;
import explorewithme.lib.in.NewCompilationDto;
import explorewithme.model.Compilation;
import explorewithme.model.Event;

import java.util.List;

public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .events(EventMapper.toShortDto(compilation.getEvents()))
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto dto, List<Event> events) {
        return Compilation.builder()
                .pinned(dto.isPinned())
                .title(dto.getTitle())
                .events(events)
                .build();
    }
}
