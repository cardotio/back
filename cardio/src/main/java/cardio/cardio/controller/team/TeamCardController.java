package cardio.cardio.controller.team;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import cardio.cardio.dto.CardDto;
import cardio.cardio.service.card.CardService;
import lombok.*;

@RestController
@RequestMapping("/teams/{teamId}/cards")
@RequiredArgsConstructor
public class TeamCardController {
    private final CardService cardService;
    /** 팀 카드 목록 가져오기 */
    @GetMapping("")
    public ResponseEntity<List<CardDto>> getCards(@PathVariable Long teamId) {
        return ResponseEntity.ok(cardService.getCards(teamId));
    }
    /** 팀에 카드 생성하기 */
    @PostMapping("")
    public ResponseEntity<CardDto> createCard(
        @PathVariable Long teamId, 
        @RequestBody CardDto cardDto){
            return ResponseEntity.ok(cardService.createCard(teamId, cardDto));
    }
    /** 팀 카드 수정 */
    @PutMapping("")
    public ResponseEntity<CardDto> updateCard(@PathVariable Long teamId, @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.updateCard(teamId, cardDto));
    }

    @PutMapping("/decks")
    public ResponseEntity<CardDto> changeCardAffiliation(@PathVariable Long teamId, @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.changeCardAffiliation(teamId, cardDto));
    }
    /** 카드 삭제 */
    @DeleteMapping("")
    public ResponseEntity<CardDto> deleteCard(@PathVariable Long teamId, @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.deleteCard(teamId, cardDto));
    }
}
