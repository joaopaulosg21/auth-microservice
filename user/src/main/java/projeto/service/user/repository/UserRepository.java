package projeto.service.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.service.user.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);
}
