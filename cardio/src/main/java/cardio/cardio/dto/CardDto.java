package cardio.cardio.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import cardio.cardio.dto.user.UserDto;
import cardio.cardio.entity.Card;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    private Long cardId;

    @NotNull
    private String cardname;
    @NotNull
    private String content;
    @NotNull
    private String type;

    private Long deckId;
    private Long priority;

    private UserDto creator;
    private TeamDto team;
    private DeckDto deck;
    private LocalDateTime createdDate;
    

    public static CardDto from(Card card) {
        if(card == null) return null;
        DeckDto deck = null;
        try {
            deck = DeckDto.from(card.getDeck());
        } catch(NullPointerException e) {
            
        }
        return CardDto.builder()
                .cardId(card.getCardId())
                .cardname(card.getCardname())
                .content(card.getContent())
                .type(card.getType().equals(true)?"public":"private")
                .creator(UserDto.from(card.getUser()))
                .team(TeamDto.from(card.getTeam()))
                .deck(deck)
                .priority(card.getPriority())
                .createdDate(card.getCreatedDate())
                .build();
     }
}
