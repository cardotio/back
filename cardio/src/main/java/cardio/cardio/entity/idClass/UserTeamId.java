package cardio.cardio.entity.idClass;

import java.io.Serializable;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTeamId implements Serializable{
    Long user;
    Long team;
}
