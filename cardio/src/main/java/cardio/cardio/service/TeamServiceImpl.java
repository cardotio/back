package cardio.cardio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cardio.cardio.dto.InviteUserDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserTeamDto;
import cardio.cardio.entity.Invitation;
import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;
import cardio.cardio.entity.UserTeam;
import cardio.cardio.exception.NotFoundException;
import cardio.cardio.repository.TeamRepository;
import cardio.cardio.repository.UserRepository;
import cardio.cardio.repository.UserTeamRepository;
import cardio.cardio.repository.InvitationRepository;
import cardio.cardio.util.SecurityUtil;
import lombok.*;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다.")));
    }
    
    /** 전체 팀 목록 가져오기 */
    @Override
    public List<TeamDto> getTeams() {
        return teamRepository.findAll().stream()
            .map(team -> TeamDto.from(team))
            .collect(Collectors.toList());
    }

    /** 팀 생성하기 */
    @Override
    @Transactional
    public TeamDto craeteTeam(TeamDto teamDto) {
        User currentUser = userRepository.findByUsername(SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다.")));

        Team team = Team.builder()
                .teamname(teamDto.getTeamname())
                .build();

        userTeamRepository.save(UserTeam.builder()
                .team(teamRepository.save(team))
                .user(currentUser)
                .build());

        return teamDto;
    }

    /** 유저 초대하기 */
    @Override
    @Transactional
    public InviteUserDto inviteUser(InviteUserDto inviteUserDto) {
        User inviteeUser = userRepository.findByUsername(inviteUserDto.getInvitee());
        // 초대하고자 하는 팀
        Team team = teamRepository.findByTeamname(inviteUserDto.getTeamname())
                .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다."));

        // 현재 유저가 가지고 있는 팀 리스트
        List<UserTeam> currentUserTeams = userTeamRepository
                .findAllByUser(userRepository.findById(getCurrentUser().getUserId()).get())
                .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다."));

        Optional<Invitation> invitation = Optional.ofNullable(null);

        // 초대하고자 하는 팀이 팀 리스트 안에 있다면 초대
        for (UserTeam userTeam : currentUserTeams) {
            if (userTeam.getTeam().getTeamId().equals(team.getTeamId())) {
                invitation = Optional.of(Invitation.builder()
                        .user(inviteeUser)
                        .team(team)
                        .build());
            }
        }

        invitationRepository.save(invitation.orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다.")));
        return InviteUserDto.builder()
                .invitee(inviteUserDto.getInvitee())
                .teamname(team.getTeamname())
                .build();
    }

    /** 받은 초대 확인하기 */
    @Override
    public List<InviteUserDto> getInvitations() {
        List<InviteUserDto> invitations = invitationRepository.findByUser(getCurrentUser()).stream()
                .map(invitation -> InviteUserDto.builder()
                        .invitee(invitation.getUser().getUsername())
                        .teamname(invitation.getTeam().getTeamname())
                        .build())
                .collect(Collectors.toList());
        return invitations;
    }

    /** 초대 수락하기 */
    @Override
    public UserTeamDto acceptInvititation(InviteUserDto inviteUserDto) {
        // Invitation invitation = Invitation.builder()
        // .user(getCurrentUser())
        // .team(teamRepository.findByTeamname(inviteUserDto.getTeamname())
        // .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다.")))
        // .build();

        for (Invitation invitation : invitationRepository.findByUser(getCurrentUser())) {
            if (invitation.getTeam().getTeamname().equals(inviteUserDto.getTeamname())) {
                invitationRepository.delete(invitation);
                userTeamRepository.save(UserTeam.builder()
                        .user(invitation.getUser())
                        .team(invitation.getTeam())
                        .build());

                return UserTeamDto.builder()
                        .username(invitation.getUser().getUsername())
                        .teamname(invitation.getTeam().getTeamname())
                        .build();
            }
        }
        throw new NotFoundException("초대를 찾을 수 없습니다.");
    }

    /** 유저의 팀 목록 가져오기 */

    

   
}
