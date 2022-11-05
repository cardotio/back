package cardio.cardio.service.team;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cardio.cardio.dto.CardDto;
import cardio.cardio.dto.DeckDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserTeamDto;
import cardio.cardio.dto.team.TeamInfoDto;
import cardio.cardio.dto.team.TeamInvitationDto;
import cardio.cardio.dto.user.UserDto;
import cardio.cardio.entity.Card;
import cardio.cardio.entity.Deck;
import cardio.cardio.entity.Invitation;
import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;
import cardio.cardio.entity.UserTeam;
import cardio.cardio.exception.NotFoundException;
import cardio.cardio.exception.UnauthorizedException;
import cardio.cardio.repository.TeamRepository;
import cardio.cardio.repository.UserRepository;
import cardio.cardio.repository.UserTeamRepository;
import cardio.cardio.repository.CardRepository;
import cardio.cardio.repository.DeckRepository;
import cardio.cardio.repository.InvitationRepository;
import cardio.cardio.util.RandomCodeGenerator;
import cardio.cardio.util.SecurityUtil;
import lombok.*;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
	private final TeamRepository teamRepository;
	private final UserTeamRepository userTeamRepository;
	private final UserRepository userRepository;
	private final InvitationRepository invitationRepository;
	private final DeckRepository deckRepository;
	private final CardRepository cardRepository;

	private User getCurrentUser() {
		return userRepository.findByUsername(SecurityUtil.getCurrentUsername()
				.orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다.")));
	}

	/** 유저가 팀 안에 속해있는지 검증 */
	private void validateUserInTeam(Long teamId) {
		teamRepository.findById(teamId)
				.orElseThrow(() -> new NotFoundException(String.format("팀을 찾을 수 없습니다 {teamId:%d}", teamId)));
		List<Long> userTeamList = userTeamRepository.findAllByUser(getCurrentUser()).get().stream()
				.map(userTeam -> userTeam.getTeam().getTeamId())
				.collect(Collectors.toList());

		if (!userTeamList.contains(teamId))
			throw new UnauthorizedException("유저가 팀 안에 속해있지 않습니다.");
	}

	/** 전체 팀 목록 가져오기 */
	@Override
	public List<TeamDto> getTeams() {
		return teamRepository.findAll().stream()
				.map(team -> TeamDto.from(team))
				.collect(Collectors.toList());
	}

	/** 팀 생성하기 */
	@Override
	@Transactional
	public TeamDto craeteTeam(String teamname) {
		User currentUser = userRepository.findByUsername(SecurityUtil.getCurrentUsername()
				.orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다.")));

		Team team = teamRepository.save(Team.builder()
			.teamname(teamname)
			.teamCode(RandomCodeGenerator.getRandomCode(6))
			.build());

		userTeamRepository.save(UserTeam.builder()
				.team(team)
				.user(currentUser)
				.build());

		return TeamDto.from(team);
	}

	/** 팀 정보 가져오기 */
	@Override
	public TeamInfoDto getTeamInfo(Long teamId) {
		validateUserInTeam(teamId);
		Team team = teamRepository.findById(teamId).get();
		List<UserTeam> userTeams = userTeamRepository.findAllByTeam(team)
				.orElseThrow(() -> new NotFoundException("팀 내의 유저를 찾을 수 없습니다."));
		return TeamInfoDto.builder()
				.teamId(teamId)
				.teamname(team.getTeamname())
				.teamCode(team.getTeamCode())
				.users(userTeams.stream()
						.map(user -> UserDto.from(user.getUser()))
						.collect(Collectors.toList()))
				.cards(cardRepository.findAllByTeam(team).get().stream()
						.map(card -> CardDto.from(card)).collect(Collectors.toList()))
				.build();
	}

	@Override
	public TeamDto getSimplifiedInfo(Long teamId) {
		Team team = teamRepository.findById(teamId).get();
		return TeamDto.from(team);
	}

	/** 팀에 유저 추가하기 */
	@Override
	public UserTeamDto addUserToTeam(Long teamId, TeamInvitationDto teamInvitationDto) {
		Team team = teamRepository.findById(teamId)
			.orElseThrow(() -> new NotFoundException(String.format("팀을 찾을 수 없습니다 {teamId:%d}", teamId)));

		if(!team.getTeamCode().equals(teamInvitationDto.getTeamCode())) 
			throw new UnauthorizedException(String.format("팀 코드가 일치하지 않습니다. {teamCode:%s}", teamInvitationDto.getTeamCode()));

		return UserTeamDto.from(userTeamRepository.save(UserTeam.builder()
				.team(team)
				.user(getCurrentUser()).build()));
	}

	/** 팀 이름을 팀 객체 가져오기 */
	@Override
	public Team getTeamByTeamId(Long teamId) {
		
		return teamRepository.findById(teamId)
				.orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다"));
	}

	/** 덱 만들기 */
	@Override
	public DeckDto createDeck(Long teamId, DeckDto deckDto) {
		validateUserInTeam(teamId);
		return DeckDto.from(deckRepository.save(Deck.builder()
				.team(teamRepository.findById(teamId).get())
				.deckname(deckDto.getDeckname())
				.build()));
	}

	/** 덱 수정하기 */
	@Override
	public DeckDto updateDeck(Long teamId, DeckDto deckDto) {
		validateUserInTeam(teamId);
		Deck deck = deckRepository.findById(deckDto.getDeckId())
				.orElseThrow(() -> new NotFoundException("덱을 찾을 수 없습니다"));
		deck.setDeckname(deckDto.getDeckname());
		return DeckDto.from(deckRepository.save(deck));
	}

	/** 덱 삭제하기 */
	@Override
	public DeckDto deleteDeck(Long teamId, DeckDto deckDto) {
		validateUserInTeam(teamId);
		Deck deck = deckRepository.findById(deckDto.getDeckId())
				.orElseThrow(() -> new NotFoundException("덱을 찾을 수 없습니다"));
		deckRepository.delete(deck);
		return DeckDto.from(deck);
	}

	@Override
	public List<DeckDto> getDecksByTeamId(Long teamId) {
		validateUserInTeam(teamId);
		return deckRepository.findAllByTeam(teamRepository.findById(teamId).get())
				.orElseThrow(() -> new NotFoundException("덱을 찾을 수 없습니다. (teamId : " + teamId + ")")).stream()
				.map(deck -> DeckDto.from(deck)).collect(Collectors.toList());
	}

	@Override
	public CardDto insertCardIntoDeck(Long teamId, Long deckId, Long cardId) {
		validateUserInTeam(teamId);

		Deck deck = deckRepository.findById(deckId)
				.orElseThrow(() -> new NotFoundException("덱을 찾을 수 없습니다. (Deck Id : " + deckId + ")"));

		Team team = teamRepository.findById(teamId)
				.orElseThrow(() -> new NotFoundException(String.format("팀을 찾을 수 없습니다 {teamId:%d}", teamId)));
		if (!deck.getTeam().equals(team))
			throw new UnauthorizedException("덱이 팀 안에 속해있지 않습니다.");

		Card card = cardRepository.findById(cardId)
				.orElseThrow(() -> new NotFoundException("카드를 찾을 수 없습니다. (Card Id : " + cardId + ")"));

		if (!card.getTeam().equals(team))
			throw new UnauthorizedException(String.format("카드가 팀 안에 속해있지 않습니다 {cardId:%d, teamId:%d}", cardId, teamId));
		card.setDeck(deck);

		return CardDto.from(cardRepository.save(card));
	}

	@Override
	public UserDto inviteUserToTeam(Long teamId, String username) {
		validateUserInTeam(teamId);
		Team team = teamRepository.findById(teamId).get();
		User invitee = userRepository.findByUsername(username);

		invitationRepository.save(Invitation.builder()
				.user(invitee)
				.team(team)
				.build());
		return UserDto.from(invitee);
	}

}
