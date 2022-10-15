package cardio.cardio.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cardio.cardio.dto.TeamDto;
import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;
import cardio.cardio.entity.UserTeam;
import cardio.cardio.exception.NotFoundMemberException;
import cardio.cardio.repository.TeamRepository;
import cardio.cardio.repository.UserRepository;
import cardio.cardio.repository.UserTeamRepository;
import cardio.cardio.util.SecurityUtil;
import lombok.*;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    /** 팀 생성하기 */
    @Override
    @Transactional
    public TeamDto craeteTeam(TeamDto teamDto) {
        User currentUser = userRepository.findByUsername(SecurityUtil.getCurrentUsername()
            .orElseThrow(() -> new NotFoundMemberException("유저를 찾을 수 없습니다.")));
        
        Team team = Team.builder()
            .teamname(teamDto.getTeamname())
            .build();
 
        userTeamRepository.save(UserTeam.builder()
            .team(teamRepository.save(team))
            .user(currentUser)
            .build());

        return teamDto;
    }
}
