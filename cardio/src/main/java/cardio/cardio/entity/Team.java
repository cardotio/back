package cardio.cardio.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "team")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
       //필드
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "team_id", unique = true, nullable = false)
       private Long teamId;
   
       @Column(length = 50, unique = true, nullable = false)
       private String teamname;
}
