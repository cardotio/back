package cardio.cardio.entity;
import javax.persistence.*;

import lombok.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    private Long commentId;

    @Column(length = 1000, name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;
}
