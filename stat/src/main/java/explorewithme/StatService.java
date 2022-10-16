package explorewithme;

import explorewithme.model.EndpointHit;
import explorewithme.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    EndpointHit saveEndpointHit(EndpointHit endpointHit);

    List<ViewStats> getStat(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
