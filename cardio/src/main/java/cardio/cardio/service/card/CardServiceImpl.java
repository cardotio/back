package cardio.cardio.service.card;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.*;
import cardio.cardio.dto.CardDto;
import cardio.cardio.entity.Card;
import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;
import cardio.cardio.exception.NotFoundException;
import cardio.cardio.exception.UnauthorizedException;
import cardio.cardio.repository.CardRepository;
import cardio.cardio.repository.TeamRepository;
import cardio.cardio.repository.UserRepository;
import cardio.cardio.repository.UserTeamRepository;
import cardio.cardio.util.SecurityUtil;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final CardRepository cardRepository;
    private final UserTeamRepository userTeamRepository;

    /** 유저, 팀, 카드 각종 예외처리 */
    private void validateCard(Long teamId, Long cardId) {
        teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(String.format("팀을 찾을 수 없습니다 {teamId:%d}", teamId)));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException(String.format("카드를 찾을 수 없습니다. {cardId:%d}", cardId)));

        if (!card.getTeam().getTeamId().equals(teamId))
            throw new UnauthorizedException(String.format("카드가 팀 안에 속해있지 않습니다.{cardId:%d, teamId:%d}", cardId, teamId));
        List<Long> userTeamList = userTeamRepository.findAllByUser(getCurrentUser()).get().stream()
                .map(userTeam -> userTeam.getTeam().getTeamId())
                .collect(Collectors.toList());
        if (!userTeamList.contains(teamId))
            throw new UnauthorizedException("유저가 팀 안에 속해있지 않습니다.");
    }

    /** 유저가 팀 안에 속해있는지 검증 */
    private void validateUserInTeam(Long teamId) {
        List<Long> userTeamList = userTeamRepository.findAllByUser(getCurrentUser()).get().stream()
                .map(userTeam -> userTeam.getTeam().getTeamId())
                .collect(Collectors.toList());

        if (!userTeamList.contains(teamId))
            throw new UnauthorizedException("유저가 팀 안에 속해있지 않습니다.");
    }

    /** 토큰을 통해 현재 유저 가져오기 */
    private User getCurrentUser() {
        return userRepository.findByUsername(SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다.")));
    }

    /** 카드 생성 */
    @Override
    public CardDto createCard(Long teamId, CardDto cardDto) {
        User user = getCurrentUser();
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다."));

        validateUserInTeam(teamId);

        Card card = Card.builder()
                .cardname(cardDto.getCardname())
                .content(cardDto.getContent())
                .type(cardDto.getType().equals("private") ? false : true)
                .user(user)
                .team(team)
                .build();
        return CardDto.from(cardRepository.save(card));

    }

    @Override
    public List<CardDto> getCards(Long teamId) {
        validateUserInTeam(teamId);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다."));

        return cardRepository.findAllByTeam(team)
                .orElseThrow(() -> new NotFoundException("카드를 찾을 수 없습니다.")).stream()
                .map(card -> CardDto.from(card)).collect(Collectors.toList());

    }

    @Override
    public CardDto updateCard(Long teamId, CardDto cardDto) {
        validateCard(teamId, cardDto.getCardId());
        Card card = cardRepository.findById(cardDto.getCardId()).get();

        card.setContent(cardDto.getContent());
        card.setCardname(cardDto.getCardname());
        card.setType(cardDto.getType().equals("public") ? true : false);
        return CardDto.from(cardRepository.save(card));
    }

    @Override
    public CardDto deleteCard(Long teamId, CardDto cardDto) {
        validateCard(teamId, cardDto.getCardId());
        Card card = cardRepository.findById(cardDto.getCardId()).get();

        cardRepository.delete(card);
        return CardDto.from(card);
    }
}
