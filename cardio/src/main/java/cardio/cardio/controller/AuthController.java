package cardio.cardio.controller;

import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.LoginDto;
import cardio.cardio.dto.TokenDto;
import cardio.cardio.dto.user.UserDto;
import cardio.cardio.jwt.JwtFilter;
import cardio.cardio.jwt.TokenProvider;
import cardio.cardio.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserServiceImpl userService;
    
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(HttpServletRequest request) {

        return ResponseEntity.ok(UserDto.from(userService.getCurrentUser()));
    }

    //request header에서 토큰 정보를 꺼내옴
   public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

    if (bearerToken.startsWith("Bearer ")) {
       return bearerToken.substring(7);
    }

    return bearerToken;
 }
}