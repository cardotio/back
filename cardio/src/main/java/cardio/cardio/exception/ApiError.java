package cardio.cardio.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    @JsonIgnore
    private String debugMessage;
    @JsonIgnore
    private List<ApiSubError> subErrors;
 
    public ApiError() {
        timestamp = LocalDateTime.now();
    }
 
    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }
 
    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        //this.message = "Unexpected error";
        this.message = ex.getLocalizedMessage();
    }
 
    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.debugMessage = ex.getLocalizedMessage();
    }
 }
 abstract class ApiSubError {

}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ApiSubError {
   private String object;
   private String field;
   private Object rejectedValue;
   private String message;

   ApiValidationError(String object, String message) {
       this.object = object;
       this.message = message;
   }
}