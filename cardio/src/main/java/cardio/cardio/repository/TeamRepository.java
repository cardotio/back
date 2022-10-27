package cardio.cardio.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Team;


public interface TeamRepository extends JpaRepository<Team, Long> {
    // Optional<Team> findByTeamname(String teamname);
}