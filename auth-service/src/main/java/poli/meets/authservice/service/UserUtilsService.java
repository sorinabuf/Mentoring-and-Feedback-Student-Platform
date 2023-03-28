package poli.meets.authservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import poli.meets.authservice.model.User;
import poli.meets.authservice.repository.UserRepository;
import poli.meets.authservice.security.JwtTokenUtil;
import poli.meets.authservice.service.dtos.UserRegisterDTO;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserUtilsService {

    private final JwtTokenUtil jwtTokenUtil;

    private final MailService mailService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;


    public User register(UserRegisterDTO userDTO) throws Exception {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }

        User savedUser = new User();

        savedUser.setUsername(userDTO.getUsername());
        savedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        savedUser.setIsActivated(false);

        userRepository.save(savedUser);

        String token = jwtTokenUtil.generateToken(userService.loadUserByUsername(userDTO.getUsername()));
        String activationUrl = "http://localhost:8080/auth/activate?token=" + token;
        mailService.sendActivationEmail(savedUser.getUsername(), activationUrl);


        return savedUser;
    }

    public void activateUser(String token) throws Exception {
        String username = jwtTokenUtil.extractUsername(token);

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        User user = userOptional.get();
        if (user.getIsActivated()) {
            throw new Exception("User already activated");
        }

        user.setIsActivated(true);
        userRepository.save(user);
    }
}
