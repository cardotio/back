package cardio.cardio.entity;

import javax.persistence.*;

import cardio.cardio.entity.idClass.UserTeamId;
import lombok.*;


@Entity
@Table(name = "user_team")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserTeamId.class)
public class UserTeam {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id", referencedColumnName = "team_id")
    private Team team;
}
