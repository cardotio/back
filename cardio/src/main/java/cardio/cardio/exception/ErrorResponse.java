package cardio.cardio.exception;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class ErrorResponse {

    private String message;
    private HttpStatus status;
    //private String code;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
    }
}