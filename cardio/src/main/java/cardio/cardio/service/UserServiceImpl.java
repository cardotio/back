package cardio.cardio.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserDto;

import org.springframework.security.crypto.password.PasswordEncoder;
import cardio.cardio.entity.Authority;
import cardio.cardio.entity.User;
import cardio.cardio.exception.DuplicateMemberException;
import cardio.cardio.exception.InvalidPasswordException;
import cardio.cardio.exception.InvalidUserException;
import cardio.cardio.exception.NotFoundException;
import cardio.cardio.repository.UserRepository;
import cardio.cardio.repository.UserTeamRepository;
import cardio.cardio.util.SecurityUtil;
import cardio.cardio.validator.PasswordValidator;
import lombok.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTeamRepository userTeamRepository;

    
    /** 유저 아이디로 유저 정보 가져오기 */
    @Override
    @Transactional
    public UserDto getUserByUserId(Long userId) {
        return UserDto.from(userRepository.findById(userId)
                .orElseThrow(/* 예외 처리 */));
    }

    /** 유저 이름으로 유저 정보 가져오기 */
    @Override
    @Transactional
    public UserDto getUserByUsername(String username) {
        return UserDto.from(userRepository.findByUsername(username));
    }

    /** 가입하기 */
    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }
        if (PasswordValidator.isValidPassword(userDto.getPassword())) {
            throw new InvalidPasswordException("유효하지 않은 비밀번호입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .displayname(userDto.getDisplayname())
                .authorities(Collections.singleton(authority))
                .email(userDto.getEmail())
                .build();

        return UserDto.from(userRepository.save(user));
    }

    /** 현재 유저의 정보 가져오기 */
    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다.")));
    }

    /** 사용자 정보 업데이트 */
    @Override
    @Transactional
    public UserDto updateUser(String username, UserDto updateUserDto) {
        if (SecurityUtil.getCurrentUsername().get().equals(username)) {
            User user = userRepository.findByUsername(username);
            user.setDisplayname(updateUserDto.getDisplayname());
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            user.setEmail(updateUserDto.getEmail());

            return UserDto.from(userRepository.save(user));
        } else {
            throw new InvalidUserException("유저가 일치하지 않습니다.");
        }

    }

    /** 모든 사용자 가져오기 */
    @Override
    public List<UserDto> getUsers() {
        List<UserDto> users = userRepository.findAll().stream()
            .map(user -> UserDto.from(user))
            .collect(Collectors.toList());
        return users;
    }

    /** 유저의 팀 목록 가져오기 */
    @Override
    public List<TeamDto> getUserTeams() {
        User user = SecurityUtil.getCurrentUsername()
            .flatMap(userRepository::findOneWithAuthoritiesByUsername)
            .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        return userTeamRepository.findAllByUser(user).
            orElseThrow(() -> new NotFoundException("유저의 팀을 찾을 수 없습니다.")).stream()
            .map(userTeam -> TeamDto.builder()
                .teamname(userTeam.getTeam().getTeamname())
                .build())
            .collect(Collectors.toList());
    }
    

}
