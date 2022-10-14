package explorewithme.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import explorewithme.lib.client.BaseClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static explorewithme.lib.util.ValidDateTime.dateToString;

@Service
public class StatClient extends BaseClient {

    private final String APP = "ExploreWithMe";

    @Autowired
    public StatClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public void saveEndpointHit(HttpServletRequest request) {
        System.out.println(request.getHeader("X-USER-URI"));
        System.out.println(request.getHeader("X-USER-IP"));
        EndpointHitDto endpointHit = new EndpointHitDto(APP,
                request.getHeader("X-USER-URI"), request.getHeader("X-USER-IP"), dateToString(LocalDateTime.now()));
        post("/hit", endpointHit);
    }
}
