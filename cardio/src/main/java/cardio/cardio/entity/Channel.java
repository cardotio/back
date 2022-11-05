package cardio.cardio.entity;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Entity
@Table(name = "channel")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id", unique = true, nullable = false)
    private Long channelId;
   
    @Column(length = 50, nullable = false)
    private String channelName;

    

    @ManyToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="team_id")
    private Team team;  
}
