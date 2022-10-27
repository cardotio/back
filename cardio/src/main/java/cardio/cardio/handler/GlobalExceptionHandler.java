package cardio.cardio.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cardio.cardio.exception.ApiError;
import cardio.cardio.exception.DuplicateMemberException;
import cardio.cardio.exception.InvalidPasswordException;
import cardio.cardio.exception.InvalidUserException;
import cardio.cardio.exception.NotFoundException;
import cardio.cardio.exception.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 아이디 중복 예외처리 - 409
    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<Object> handleNoSuchElementFoundException(DuplicateMemberException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex));
    }
    // 비밀번호 유효성 예외처리 - 400
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handleNoSuchElementFoundException(InvalidPasswordException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex));
    }
    // 유저정보가 유효하지 않음 - 400
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Object> handleNoSuchElementFoundException(InvalidUserException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, ex));
    }

    // 찾을 수 없음  - 404
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNoSuchElementFoundException(NotFoundException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex));
    }

    // 인증이 유효하지 않음 - 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleNoSuchElementFoundException(UnauthorizedException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, ex));
    }
    
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
 
} 