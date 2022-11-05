package cardio.cardio.service.team;
import java.util.List;


import cardio.cardio.dto.CardDto;
import cardio.cardio.dto.DeckDto;

import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserTeamDto;
import cardio.cardio.dto.team.TeamInfoDto;
import cardio.cardio.dto.team.TeamInvitationDto;
import cardio.cardio.dto.user.UserDto;
import cardio.cardio.entity.Team;

public interface TeamService {
    public List<TeamDto> getTeams();
    public UserTeamDto addUserToTeam(Long teamId, TeamInvitationDto teamInvitationDto);

    public TeamInfoDto getTeamInfo(Long teamId);
    public TeamDto getSimplifiedInfo(Long teamId);

    public TeamDto craeteTeam(String teamname);
    
    public Team getTeamByTeamId(Long teamId);
    
    public List<DeckDto> getDecksByTeamId(Long teamId);
    public DeckDto createDeck(Long teamId, DeckDto deckDto);
    public DeckDto updateDeck(Long teamId, DeckDto deckDto);
    public DeckDto deleteDeck(Long teamId, DeckDto deckDto);
    
    public CardDto insertCardIntoDeck(Long teamId, Long deckId, Long cardId);
    public UserDto inviteUserToTeam(Long teamId, String username);
}
