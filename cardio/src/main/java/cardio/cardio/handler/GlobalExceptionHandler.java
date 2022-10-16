package cardio.cardio.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cardio.cardio.exception.DuplicateMemberException;
import cardio.cardio.exception.InvalidPasswordException;
import cardio.cardio.exception.InvalidUserException;
import cardio.cardio.exception.NotFoundException;
import cardio.cardio.exception.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 아이디 중복 예외처리 - 409
    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(DuplicateMemberException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
    // 비밀번호 유효성 예외처리 - 400
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(InvalidPasswordException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    // 유저정보가 유효하지 않음 - 400
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(InvalidUserException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    // 찾을 수 없음  - 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    // 인증이 유효하지 않음 - 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(UnauthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }
    
}