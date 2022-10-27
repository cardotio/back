package cardio.cardio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Invitation;
import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
    List<Invitation> findAllByUser(User user);
    List<Invitation> findAllByTeam(Team team);
}