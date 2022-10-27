package cardio.cardio.dto;
import cardio.cardio.entity.Invitation;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteUserDto {
    private String teamname;
    private String invitee;

    public static InviteUserDto from(Invitation invitation) {
        if(invitation.equals(null)) {
            return null;
        }
        return InviteUserDto.builder()
            .teamname(invitation.getTeam().getTeamname())
            .invitee(invitation.getUser().getUsername())
            .build();
    }
}
