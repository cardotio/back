package cardio.cardio.controller.team;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.CardDto;
import cardio.cardio.dto.DeckDto;
import cardio.cardio.service.team.TeamService;
import lombok.*;

@RestController
@RequestMapping("/teams/{teamId}/decks")
@RequiredArgsConstructor
public class TeamDeckController {
    private final TeamService teamService;
    /** 덱 가져오기 */
    @GetMapping("")
    public ResponseEntity<List<DeckDto>> getDecks(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getDecksByTeamId(teamId));
    }
    /** 덱 생성하기 */
    @PostMapping("")
    public ResponseEntity<DeckDto> createDeck(
        @PathVariable Long teamId, 
        @RequestBody DeckDto deckDto) {
            return ResponseEntity.ok(teamService.createDeck(teamId, deckDto));
    }
    /** 덱 수정하기 */
    //@PutMapping("")
    
    
    //public ResponseEntity<DeckDto> updateDeck 
    /** 덱 삭제하기 */
    //@DeleteMapping("")

    /** 덱 안에 카드 넣기 */
    @PostMapping("/{deckId}/cards")
    public ResponseEntity<CardDto> insertCardIntoDeck(
        @PathVariable Long teamId,
        @PathVariable Long deckId,
        @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(teamService.insertCardIntoDeck(teamId, deckId, cardDto.getCardId()));
    }
}
