package explorewithme.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import explorewithme.lib.client.BaseClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class StatClient extends BaseClient {

    private final String app = "ExploreWithMe";

    private final String pattern;

    @Autowired
    public StatClient(@Value("${stat-server.url}") String serverUrl,
                      RestTemplateBuilder builder,
                      @Value("${dateTimeFormat}") String pattern) {
        super(serverUrl, builder, "/");
        this.pattern = pattern;
    }

    public void saveEndpointHit(HttpServletRequest request) {
        EndpointHitDto endpointHit = new EndpointHitDto(app,
                request.getHeader("X-USER-URI"),
                request.getHeader("X-USER-IP"),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)));
        post("/hit", endpointHit);
    }
}
