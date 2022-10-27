package cardio.cardio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Card;
import cardio.cardio.entity.Team;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<List<Card>> findAllByTeam(Team team);
}