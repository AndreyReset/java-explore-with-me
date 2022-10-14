package explorewithme.p_admin.user;

import explorewithme.dto.UserDto;
import explorewithme.model.User;

import java.util.List;

public interface AdminUserService {

    List<UserDto> getUsers(Long[] ids, Integer from, Integer size);

    UserDto createUser(User user);

    void deleteUser(long userId);
}
