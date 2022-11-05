package cardio.cardio.dto.team;

import java.util.List;

import cardio.cardio.dto.user.UserDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamUsersDto {
    Long teamId;
    String teamname;
    String teamCode;
    List<UserDto> users;
}
