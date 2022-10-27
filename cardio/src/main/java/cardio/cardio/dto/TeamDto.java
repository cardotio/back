package cardio.cardio.dto;
import cardio.cardio.entity.Team;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private Long teamId;
    private String teamname;

    public static TeamDto from(Team team) {
        if(team.equals(null)) return null;

        return TeamDto.builder()
            .teamId(team.getTeamId())
            .teamname(team.getTeamname())
            .build();
    }
}
