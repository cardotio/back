package cardio.cardio.entity;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;
@Entity
@Table(name = "deck")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id", unique = true, nullable = false)
    private Long deckId;
   
    @Column(length = 50, nullable = false)
    private String deckname;

    @ManyToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="team_id")
    private Team team;
}
