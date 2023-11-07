package projeto.service.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import projeto.service.user.configuration.TokenService;
import projeto.service.user.dto.LoginDTO;
import projeto.service.user.dto.TokenDTO;
import projeto.service.user.exceptions.EmailAlreadyUsedException;
import projeto.service.user.model.User;
import projeto.service.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final LoginService loginService;

    private final TokenService tokenService;

    public User create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if(optionalUser.isPresent()) {
            throw new EmailAlreadyUsedException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public TokenDTO login(LoginDTO loginDTO) {
        User user = loginService.authenticate(loginDTO);
        String token = tokenService.generate(user);
        return new TokenDTO("Bearer",token);
    }
}
