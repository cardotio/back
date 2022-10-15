package cardio.cardio.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.UserDto;
import cardio.cardio.service.UserService;
import lombok.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /** 유저 정보 불러오기 */
    @GetMapping("")
    public ResponseEntity<UserDto> getUsers(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getCurrentUser());
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
}
