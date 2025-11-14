package fr.epsi.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.epsi.spring.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}

