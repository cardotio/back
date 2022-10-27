package cardio.cardio.service.card;

import java.util.List;

import cardio.cardio.dto.CardDto;

public interface CardService {
    CardDto createCard(Long teamId, CardDto cardDto);
    CardDto updateCard(Long teamId, CardDto cardDto);
    CardDto deleteCard(Long teamId, CardDto cardDto);
    List<CardDto> getCards(Long teamId);

}
