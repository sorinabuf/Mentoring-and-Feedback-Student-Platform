package poli.meets.authservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import poli.meets.authservice.client.MailClient;
import poli.meets.authservice.model.User;
import poli.meets.authservice.repository.RoleRepository;
import poli.meets.authservice.repository.UserRepository;
import poli.meets.authservice.security.JwtTokenUtil;
import poli.meets.authservice.service.dtos.ChangePasswordDTO;
import poli.meets.authservice.service.dtos.EmailDTO;
import poli.meets.authservice.service.dtos.UserRegisterDTO;
import poli.meets.authservice.web.error.ForbiddenException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserUtilsService {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final MailClient mailClient;

    private final RoleRepository roleRepository;

    public User register(UserRegisterDTO userDTO) throws Exception {
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());

        if (user.isPresent() && user.get().getIsActivated()) {
            throw new Exception("Username already exists");
        }

        User savedUser = user.orElse(new User());

        savedUser.setUsername(userDTO.getUsername());
        savedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        savedUser.setIsActivated(false);

        userRepository.save(savedUser);

        String token = jwtTokenUtil.generateToken(userService.loadUserByUsername(userDTO.getUsername()));
        String activationUrl = "http://localhost:8080/auth/api/activate?token=" + token;

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setTo(userDTO.getUsername());
        emailDTO.setSubject("PoliMeets account activation");
        emailDTO.setBody("Click on the following link to activate your account: " + activationUrl);

        mailClient.sendEmail(emailDTO);

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

    public boolean isActivated(String username) {
        return userRepository.findByUsername(username).map(User::getIsActivated).orElse(false);
    }

    public Boolean changePassword(String username, ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ForbiddenException("Invalid token"));

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(),
                user.getPassword())) {
            throw new ForbiddenException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);

        return true;
    }

    public boolean isCurrentUserAdmin(String username) {
        return roleRepository.findRolesByUsername(username).stream()
                .anyMatch(role -> role.getName().equals("ADMIN"));
    }
}
