package cardio.cardio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.UserTeam;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long>{
    
}
