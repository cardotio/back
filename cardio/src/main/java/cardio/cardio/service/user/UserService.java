package cardio.cardio.service.user;

import java.util.List;

import cardio.cardio.dto.InviteUserDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.user.DetailUserDto;
import cardio.cardio.dto.user.UserDto;
import cardio.cardio.entity.User;

public interface UserService {
    User getUserByUserId(Long userId);
    DetailUserDto getUserByUsername(String username);
    User getSimpleUserByUsername(String username);
    UserDto createUser(UserDto userDto);
    User getCurrentUser();
    List<UserDto> getUsers();
    UserDto updateUser(UserDto updateUserDto);
    List<TeamDto> getUserTeams();   
    UserDto deleteUser();
    List<InviteUserDto> getInvitations();
}
