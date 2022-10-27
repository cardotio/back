package cardio.cardio.dto;
import cardio.cardio.entity.Deck;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeckDto {
    private Long deckId;
    private String deckname;


    public static DeckDto from(Deck deck) {
        if(deck.equals(null)) return null;

        return DeckDto.builder()
            .deckId(deck.getDeckId())
            .deckname(deck.getDeckname())
            .build();
    }
}
