package cardio.cardio.dto;
import cardio.cardio.entity.UserTeam;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTeamDto {
    private String username;
    private String teamname;

    public static UserTeamDto from(UserTeam userTeam) {
        if(userTeam.equals(null)) {
            return null;
        }
        return UserTeamDto.builder()
            .username(userTeam.getUser().getUsername())
            .teamname(userTeam.getTeam().getTeamname())
            .build();
    }
}
