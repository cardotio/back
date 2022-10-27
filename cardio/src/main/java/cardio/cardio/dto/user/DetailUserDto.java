package cardio.cardio.dto.user;
import com.fasterxml.jackson.annotation.JsonProperty;

import cardio.cardio.dto.AuthorityDto;
import cardio.cardio.dto.team.TeamUsersDto;

import lombok.*;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DetailUserDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull
	@Size(min = 8, max = 100)
	private String password;

	@NotNull
	@Size(min = 5, max = 50)
	private String username;

	@NotNull
	@Size(min = 3, max = 50)
	private String displayname;

	@NotNull
	@Size(min = 5, max = 50)
	private String email;
    
	@NotNull
	@Size(min = 2, max = 50)
	private String role;

    private List<TeamUsersDto> teams;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<AuthorityDto> authorityDtoSet;

	// public static DetailUserDto from(User user) {
	// 	if (user == null)
	// 		return null;

	// 	return DetailUserDto.builder()
	// 			.username(user.getUsername())
	// 			.displayname(user.getDisplayname())
	// 			.email(user.getEmail())
	// 			.authorityDtoSet(user.getAuthorities().stream()
	// 					.map(authority -> AuthorityDto.builder()
	// 							.authorityName(authority.getAuthorityName()).build())
	// 					.collect(Collectors.toSet()))

	// 			.teams(user.getUserTeams().stream()
	// 				.map(userTeam -> TeamUsersDto.builder()
    //                     .teamname(userTeam.getTeam().getTeamname())
    //                     .users(getUsers(userTeam.getTeam())).build())
	// 				.collect(Collectors.toList()))

	// 			.invitations(user.getInvitations().stream()
	// 				.map(invitation -> InviteUserDto.builder()
	// 					.teamname(invitation.getTeam().getTeamname())
	// 					.invitee(invitation.getUser().getUsername())
	// 					.build())
	// 					.collect(Collectors.toList()))
	// 			.build();
	// }
}
