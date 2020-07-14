package authweb.repository;

import authweb.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {

    boolean existsByToken(String token);
}
