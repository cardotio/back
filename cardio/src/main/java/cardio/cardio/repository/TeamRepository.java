package cardio.cardio.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Team;


public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamname(String teamname);
}