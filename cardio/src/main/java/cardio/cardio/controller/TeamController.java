package cardio.cardio.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.InviteUserDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserTeamDto;
import cardio.cardio.service.TeamService;
import lombok.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final  TeamService teamService;

    /** 전체 팀 목록 가져오기 */
    @GetMapping("")
    public ResponseEntity<List<TeamDto>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    /** 팀 생성하기 */
    @PostMapping("")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.craeteTeam(teamDto));
    }
    /** 팀에 유저 초대하기 */
    @PostMapping("/invites")
    public ResponseEntity<InviteUserDto> inviteUser(@RequestBody InviteUserDto inviteUserDto) {
        return ResponseEntity.ok(teamService.inviteUser(inviteUserDto));
    }
    /** 받은 초대 목록 가져오기 */
    @GetMapping("/invites")
    public ResponseEntity<List<InviteUserDto>> getInvitations() {
        return ResponseEntity.ok(teamService.getInvitations());
    }
    /** 받은 초대 수락하기 */
    @PostMapping("/invites/accept")
    public ResponseEntity<UserTeamDto> acceptInvitation(@RequestBody InviteUserDto inviteUserDto) {
        return ResponseEntity.ok(teamService.acceptInvititation(inviteUserDto));
    }
}