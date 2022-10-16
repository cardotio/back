package cardio.cardio.entity;

import javax.persistence.*;

import cardio.cardio.entity.idClass.InvitationId;
import lombok.*;


@Entity
@Table(name = "invitation")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(InvitationId.class)
public class Invitation {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id", referencedColumnName = "team_id")
    private Team team;

}
