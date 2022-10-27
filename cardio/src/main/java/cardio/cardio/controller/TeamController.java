package cardio.cardio.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.CardDto;
import cardio.cardio.dto.DeckDto;
import cardio.cardio.dto.MessageDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserTeamDto;
import cardio.cardio.dto.team.TeamInfoDto;
import cardio.cardio.service.card.CardService;
import cardio.cardio.service.message.MessageService;
import cardio.cardio.service.team.TeamService;
import lombok.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final  TeamService teamService;
    private final CardService cardService;
    private final MessageService messageService;
    /** 전체 팀 목록 가져오기 */
    @GetMapping("")
    public ResponseEntity<List<TeamDto>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    /** 팀 생성하기 */
    @PostMapping("")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.craeteTeam(teamDto.getTeamname()));
    }
    
    /** 특정 팀 정보 가져오기 */
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamInfoDto> getTeamInfo(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getTeamInfo(teamId));
    }
    /** 팀 카드 목록 가져오기 */
    @GetMapping("/{teamId}/cards")
    public ResponseEntity<List<CardDto>> getCards(@PathVariable Long teamId) {
        return ResponseEntity.ok(cardService.getCards(teamId));
    }
    /** 팀에 카드 생성하기 */
    @PostMapping("/{teamId}/cards")
    public ResponseEntity<CardDto> createCard(
        @PathVariable Long teamId, 
        @RequestBody CardDto cardDto){
            return ResponseEntity.ok(cardService.createCard(teamId, cardDto));
    }
    /** 팀 카드 수정 */
    @PutMapping("/{teamId}/cards")
    public ResponseEntity<CardDto> updateCard(@PathVariable Long teamId, @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.updateCard(teamId, cardDto));
    }
    /** 카드 삭제 */
    @DeleteMapping("/{teamId}/cards")
    public ResponseEntity<CardDto> deleteCard(@PathVariable Long teamId, @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.deleteCard(teamId, cardDto));
    }
    /** 유저 팀에 추가하기 */
    @PostMapping("/{teamId}/users")
    public ResponseEntity<UserTeamDto> addUserToTeam(
        @PathVariable Long teamId){
            return ResponseEntity.ok(teamService.addUserToTeam(teamId));
    }

    /** 팀 내의 채팅 가져오기 */
    @GetMapping("/{teamId}/messages") 
    public ResponseEntity<List<MessageDto>> getTeamMessages(@PathVariable Long teamId) {
        return ResponseEntity.ok(messageService.getTeamMessages(teamId));
    }

    /** 팀의 덱 가져오기 */
    @GetMapping("/{teamId}/decks")
    public ResponseEntity<List<DeckDto>> getDecks(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getDecksByTeamId(teamId));
    }
    /** 팀에 덱 생성하기 */
    @PostMapping("/{teamId}/decks")
    public ResponseEntity<DeckDto> createDeck(
        @PathVariable Long teamId, 
        @RequestBody DeckDto deckDto) {
            return ResponseEntity.ok(teamService.createDeck(teamId, deckDto.getDeckname()));
    }
    /** 덱 안에 카드 넣기 */
    @PostMapping("/{teamId}/decks/{deckId}/cards")
    public ResponseEntity<CardDto> insertCardIntoDeck(
        @PathVariable Long teamId,
        @PathVariable Long deckId,
        @RequestBody CardDto cardDto
    ) {
        return ResponseEntity.ok(teamService.insertCardIntoDeck(teamId, deckId, cardDto.getCardId()));
    }
}