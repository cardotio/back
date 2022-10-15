package cardio.cardio.service;

import cardio.cardio.dto.UserDto;

public interface UserService {
    public UserDto getUserByUserId(Long userId);
    public UserDto getUserByUsername(String username);
    public UserDto createUser(UserDto userDto);
    public UserDto getCurrentUser();
    public UserDto updateUser(String username, UserDto updateUserDto);
}
