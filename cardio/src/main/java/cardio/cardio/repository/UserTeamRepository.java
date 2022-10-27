package cardio.cardio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;
import cardio.cardio.entity.UserTeam;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long>{
    Optional<List<UserTeam>> findAllByUser(User user);
    Optional<List<UserTeam>> findAllByTeam(Team team);
}
