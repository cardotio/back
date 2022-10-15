package cardio.cardio.entity;

import lombok.*;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", unique = true, nullable = false)
    private Long messageId;

    @Column(length = 1000, name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;
}
