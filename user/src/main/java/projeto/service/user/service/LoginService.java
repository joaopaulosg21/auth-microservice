package projeto.service.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import projeto.service.user.dto.LoginDTO;
import projeto.service.user.model.User;
import projeto.service.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User authenticate(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.email()).orElseThrow();

        if(!passwordEncoder.matches(loginDTO.password(),user.getPassword())) {
            throw new RuntimeException("Login exception");
        }

        return user;
    }
}
