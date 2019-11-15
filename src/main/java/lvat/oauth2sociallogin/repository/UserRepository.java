package lvat.oauth2sociallogin.repository;

import lvat.oauth2sociallogin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findById(Long id);
}
