package explorewithme.p_admin.compilation;

import explorewithme.dto.CompilationDto;
import explorewithme.lib.in.NewCompilationDto;

public interface AdminCompilationService {

    CompilationDto createCompilation(NewCompilationDto dto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    void unpinToMainPage(long compId);

    void pinToMainPage(long compId);
}
