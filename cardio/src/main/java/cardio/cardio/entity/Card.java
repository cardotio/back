package cardio.cardio.entity;
import javax.persistence.*;

import lombok.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", unique = true, nullable = false)
    private Long cardId;

    @Column(length = 50, nullable = false)
    private String cardname;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean type;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;
}

