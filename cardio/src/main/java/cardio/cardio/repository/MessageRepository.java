package cardio.cardio.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Message;
import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<List<Message>> findAllBySender(User sender);
    Optional<List<Message>> findAllByTeam(Team team);
}