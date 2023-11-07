package projeto.service.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.service.user.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
