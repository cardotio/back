package cardio.cardio.service;

import java.util.List;

import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserDto;

public interface UserService {
    public UserDto getUserByUserId(Long userId);
    public UserDto getUserByUsername(String username);
    public UserDto createUser(UserDto userDto);
    public UserDto getCurrentUser();
    public List<UserDto> getUsers();
    public UserDto updateUser(String username, UserDto updateUserDto);
    public List<TeamDto> getUserTeams();
    
}
