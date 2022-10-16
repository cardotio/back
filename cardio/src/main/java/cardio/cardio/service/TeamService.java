package cardio.cardio.service;
import java.util.List;

import cardio.cardio.dto.InviteUserDto;
import cardio.cardio.dto.TeamDto;
import cardio.cardio.dto.UserTeamDto;

public interface TeamService {
    public List<TeamDto> getTeams();
    public TeamDto craeteTeam(TeamDto teamDto);
    public InviteUserDto inviteUser(InviteUserDto inviteUserDto);
    public List<InviteUserDto> getInvitations();
    public UserTeamDto acceptInvititation(InviteUserDto inviteUserDto);
}
