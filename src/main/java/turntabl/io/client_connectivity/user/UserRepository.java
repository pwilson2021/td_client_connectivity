package turntabl.io.client_connectivity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT s FROM users s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);
}
