package cardio.cardio.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserDto;
import cardio.cardio.service.UserService;
import lombok.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /** 모든 유저 정보 불러오기 */
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getUsers(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUsers());
    }

    /** 유저 정보 등록하기 */
    @PostMapping("")
    public ResponseEntity<UserDto> insertUser(
            @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    /** 특정 유저 정보 불러오기 */
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable String username,
            HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    /** 유저 정보 수정하기 */
    @PutMapping("/{username}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable String username,
            @RequestBody UserDto updateUserDto) {
        return ResponseEntity.ok(userService.updateUser(username, updateUserDto));
    }

    /** 유저의 팀 목록 가져오기 */
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDto>> getUserTeams() {
        return ResponseEntity.ok(userService.getUserTeams());
    }


}
    
