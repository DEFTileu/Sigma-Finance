package kz.javazhan.sigma_finance.services.serviceImpl;

import kz.javazhan.sigma_finance.components.JwtUtils;
import kz.javazhan.sigma_finance.domain.DTOS.AuthRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.AuthResponseDTO;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.domain.enums.UserRole;
import kz.javazhan.sigma_finance.mappers.UserMapper;
import kz.javazhan.sigma_finance.repositories.UserRepository;
import kz.javazhan.sigma_finance.services.AccountService;
import kz.javazhan.sigma_finance.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;


    @Override
    public AuthResponseDTO register(AuthRequestDTO request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());

        if (existingUser.isPresent()){
            throw new BadCredentialsException("User with email: "+request.getUsername()+" already exists!!!");
        }

        User user = userMapper.toUser(request);
        user.setRoles(List.of(UserRole.USER_ROLE));


        log.info("Registering user: {}", user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        accountService.createDefoultAccount2NewUser(user);
        String accessToken = jwtUtils.generateToken(request.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(request.getUsername());



        return AuthResponseDTO.builder()
                .user(userMapper.toUserDTO(user))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not found by email: " + request.getUsername()));

        String accessToken = jwtUtils.generateToken(request.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(request.getUsername());

        return AuthResponseDTO.builder()
                .user(userMapper.toUserDTO(user))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public String logout(String refreshToken) {
        return "";
    }
}
