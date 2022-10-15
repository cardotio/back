package cardio.cardio.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import cardio.cardio.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUserId(Long userId);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    User findByUsername(String username);
}