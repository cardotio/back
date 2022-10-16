package cardio.cardio.dto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTeamDto {
    private String username;
    private String teamname;
}
