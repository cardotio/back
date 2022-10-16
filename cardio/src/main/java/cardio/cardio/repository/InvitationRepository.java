package cardio.cardio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Invitation;
import cardio.cardio.entity.User;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
    List<Invitation> findByUser(User user);
}