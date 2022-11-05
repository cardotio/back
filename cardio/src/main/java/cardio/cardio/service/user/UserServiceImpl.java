package cardio.cardio.service.user;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cardio.cardio.dto.AuthorityDto;
import cardio.cardio.dto.InviteUserDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.team.TeamUsersDto;
import cardio.cardio.dto.user.DetailUserDto;
import cardio.cardio.dto.user.UserDto;

import org.springframework.security.crypto.password.PasswordEncoder;
import cardio.cardio.entity.Authority;
import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;
import cardio.cardio.exception.DuplicateMemberException;
import cardio.cardio.exception.InvalidPasswordException;
import cardio.cardio.exception.NotFoundException;
import cardio.cardio.repository.InvitationRepository;
import cardio.cardio.repository.UserRepository;
import cardio.cardio.repository.UserTeamRepository;
import cardio.cardio.util.SecurityUtil;
import cardio.cardio.validator.PasswordValidator;
import lombok.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTeamRepository userTeamRepository;
    // private final TeamRepository teamRepository;
    private final InvitationRepository invitationRepository;

    /** 유저 이름으로 유저 정보 가져오기 */
    @Override
    @Transactional
    public DetailUserDto getUserByUsername(String username) {
        User user = username.equals("me")? getCurrentUser():userRepository.findByUsername(username);

        // 유저가 속해있는 팀들
        List<Team> teams = userTeamRepository.findAllByUser(user).get().stream()
            .map(userTeam -> userTeam.getTeam())
            .collect(Collectors.toList());

        // 유저가 속해있는 팀들 안에 있는 유저들 리스트
        List<TeamUsersDto> teamUsers = teams.stream()
            .map(team -> TeamUsersDto.builder()
                .teamCode(team.getTeamCode())
                .teamId(team.getTeamId())
                .teamname(team.getTeamname())
                .users(userTeamRepository.findAllByTeam(team).get().stream()
                    .map(userTeam -> UserDto.from(userTeam.getUser()))
                    .collect(Collectors.toList())).build())
                    .collect(Collectors.toList());
                
                
        return DetailUserDto.builder()
            .username(user.getUsername())
            .displayname(user.getDisplayname())
            .email(user.getEmail())
            .authorityDtoSet(user.getAuthorities().stream()
                .map(authority -> AuthorityDto.builder()
                    .authorityName(authority.getAuthorityName()).build())
            .collect(Collectors.toSet()))
            .role(user.getRole())
            .teams(teamUsers)
            .build();
     
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
                .role(userDto.getRole())
                .status("activated")
                .description("Nice to meet you!")
                .build();

        return UserDto.from(userRepository.save(user));
    }

    /** 현재 유저의 정보 가져오기 */
    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
    }

    /** 사용자 정보 업데이트 */
    @Override
    @Transactional
    public UserDto updateUser(UserDto updateUserDto) {
        String username = getCurrentUser().getUsername();
        User user = userRepository.findByUsername(username);
            user.setDisplayname(updateUserDto.getDisplayname());
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            user.setRole(updateUserDto.getRole());
            user.setEmail(updateUserDto.getEmail());

            return UserDto.from(userRepository.save(user));

    }

    /** 모든 사용자 가져오기 */
    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
            .map(user -> UserDto.from(user))
            .collect(Collectors.toList());
    }

    /** 유저의 팀 목록 가져오기 */
    @Override
    public List<TeamDto> getUserTeams() {
        User user = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        return userTeamRepository.findAllByUser(user).orElseThrow(() -> new NotFoundException("유저의 팀을 찾을 수 없습니다."))
                .stream()
                .map(userTeam -> TeamDto.from(userTeam.getTeam()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto deleteUser() {
        User user = getCurrentUser(); 
        userRepository.delete(user);
        return UserDto.from(user);
    }

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
    }

    @Override
    public List<InviteUserDto> getInvitations() {
        User currentUser = getCurrentUser();
        return invitationRepository.findAllByUser(currentUser).stream()
        .map(invitation -> InviteUserDto.from(invitation))
        .collect(Collectors.toList());
    }

    @Override
    public User getSimpleUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
