package cardio.cardio.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "card_tag")
public class CardTag implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name="card_id")
    private Card card;

    @Id
    @ManyToOne
    @JoinColumn(name="tag_id")
    private Tag tag;
}