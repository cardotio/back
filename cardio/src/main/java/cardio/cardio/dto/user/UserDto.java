package cardio.cardio.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import cardio.cardio.dto.AuthorityDto;
import cardio.cardio.entity.User;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {

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

	private String description;
	// private List<TeamDto> teams;
	// private List<InviteUserDto> invitations;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<AuthorityDto> authorityDtoSet;

	public static UserDto from(User user) {
		if (user == null)
			return null;

		return UserDto.builder()
				.username(user.getUsername())
				.displayname(user.getDisplayname())
				.email(user.getEmail())
				.authorityDtoSet(user.getAuthorities().stream()
						.map(authority -> AuthorityDto.builder()
								.authorityName(authority.getAuthorityName()).build())
						.collect(Collectors.toSet()))
				.role(user.getRole())
				.description(user.getDescription())

				// .teams(user.getUserTeams().stream()
				// 	.map(userTeam -> TeamDto.from(userTeam.getTeam()))
				// 	.collect(Collectors.toList()))

				// .invitations(user.getInvitations().stream()
				// 	.map(invitation -> InviteUserDto.builder()
				// 		.teamname(invitation.getTeam().getTeamname())
				// 		.invitee(invitation.getUser().getUsername())
				// 		.build())
				// 		.collect(Collectors.toList()))
				.build();
	}
}
