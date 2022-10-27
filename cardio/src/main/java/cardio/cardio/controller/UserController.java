package cardio.cardio.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.InviteUserDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.user.DetailUserDto;
import cardio.cardio.dto.user.UserDto;
import cardio.cardio.service.user.UserService;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;



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

    /** 현재 유저 정보 불러오기 */    
    @GetMapping("/me")
    public ResponseEntity<DetailUserDto> getCurrent(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUserByUsername("me"));
    }
    
    /** 유저 정보 등록하기 */
    @PostMapping("")
    public ResponseEntity<UserDto> insertUser(
            @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    /** 특정 유저 정보 불러오기 */
    @GetMapping("/{username}")
    public ResponseEntity<DetailUserDto> getUser(
            @PathVariable String username,
            HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    /** 유저 정보 수정하기 */
    @PutMapping("/me")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto updateUserDto) {
        return ResponseEntity.ok(userService.updateUser(updateUserDto));
    }

    /** 유저의 팀 목록 가져오기 */
    @GetMapping("/me/teams")
    public ResponseEntity<List<TeamDto>> getUserTeams() {
        return ResponseEntity.ok(userService.getUserTeams());
    }

    /** 유저 삭제 */
    @DeleteMapping("/me")
    public ResponseEntity<UserDto> deleteUser() {
        return ResponseEntity.ok(userService.deleteUser());
    }

    /** 초대받은 팀 목록 조회 */
    @GetMapping("/me/invitations")
    public ResponseEntity<List<InviteUserDto>> getMethodName(@PathVariable String username) {
        return ResponseEntity.ok(userService.getInvitations());
    }
    
}
    
