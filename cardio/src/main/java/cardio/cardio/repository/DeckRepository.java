package cardio.cardio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Deck;
import cardio.cardio.entity.Team;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    Optional<List<Deck>> findAllByTeam(Team team);
}