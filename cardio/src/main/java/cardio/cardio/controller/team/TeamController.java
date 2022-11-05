package cardio.cardio.controller.team;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.MessageDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserTeamDto;
import cardio.cardio.dto.team.TeamInfoDto;
import cardio.cardio.dto.team.TeamInvitationDto;
import cardio.cardio.dto.user.UserDto;
import cardio.cardio.service.message.MessageService;
import cardio.cardio.service.team.TeamService;
import lombok.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final  TeamService teamService;
    
    private final MessageService messageService;
    /** 전체 팀 목록 가져오기 */
    @GetMapping("")
    public ResponseEntity<List<TeamDto>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    /** 팀 생성하기 */
    @PostMapping("")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.craeteTeam(teamDto.getTeamname()));
    }
    
    /** 특정 팀 정보 가져오기 */
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamInfoDto> getTeamInfo(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getTeamInfo(teamId));
    }
    @GetMapping("/simple/{teamId}")
    public ResponseEntity<TeamDto> getSimplifiedTeamInfo(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getSimplifiedInfo(teamId));
    }
    /** 유저 팀에 초대 */
    @PostMapping("/{teamId}/invite/users")
    public ResponseEntity<UserDto> inviteUserToTeam(
        @PathVariable Long teamId,
        @RequestBody UserDto userDto) {
            return ResponseEntity.ok(teamService.inviteUserToTeam(teamId, userDto.getUsername()));
        }

    /** 유저 팀에 추가하기 */
    @PostMapping("/{teamId}/users")
    public ResponseEntity<UserTeamDto> addUserToTeam(
        @PathVariable Long teamId,
        @RequestBody TeamInvitationDto teamInvitationDto){
            return ResponseEntity.ok(teamService.addUserToTeam(teamId, teamInvitationDto));
    }

    /** 팀 내의 채팅 가져오기 */
    @GetMapping("/{teamId}/messages") 
    public ResponseEntity<List<MessageDto>> getTeamMessages(@PathVariable Long teamId) {
        return ResponseEntity.ok(messageService.getTeamMessages(teamId));
    }
}