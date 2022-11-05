package cardio.cardio.dto.team;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamInvitationDto {
    private Long teamId;
    private String teamCode;
}
