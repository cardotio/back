package cardio.cardio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}