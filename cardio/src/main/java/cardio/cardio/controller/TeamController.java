package cardio.cardio.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.TeamDto;
import cardio.cardio.service.TeamService;
import lombok.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final  TeamService teamService;

    /** 유저 정보 불러오기 */
    @PostMapping("")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.craeteTeam(teamDto));
    }

}