package explorewithme.p_admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import explorewithme.lib.client.BaseClient;
import explorewithme.lib.in.NewUserRequest;

import java.util.Map;

import static explorewithme.lib.util.DataToLine.arrToLine;

@Service
public class AdminClientUsers extends BaseClient {

    @Autowired
    public AdminClientUsers(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> getUsers(Long[] ids, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "ids", arrToLine(ids),
                "from", from,
                "size", size
        );
        return get("admin/users?ids={ids}&&from={from}&&size={size}", parameters);
    }

    public ResponseEntity<Object> createUser(NewUserRequest userRequest) {
        return post("admin/users", userRequest);
    }

    public ResponseEntity<Object> deleteUser(long userId) {
        return delete("admin/users/" + userId);
    }
}
