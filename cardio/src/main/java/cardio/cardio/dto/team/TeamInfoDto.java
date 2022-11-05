package cardio.cardio.dto.team;
import java.util.List;

import cardio.cardio.dto.CardDto;
import cardio.cardio.dto.user.UserDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamInfoDto {
    private Long teamId;
    private String teamCode;
    private String teamname;
    private List<UserDto> users;
    private List<CardDto> cards;

}
