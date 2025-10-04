package kz.javazhan.sigma_finance.services.serviceImpl;

import kz.javazhan.sigma_finance.domain.DTOS.UserDTO;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.repositories.UserRepository;
import kz.javazhan.sigma_finance.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Пользователь не аутентифицирован");
        }

        String email = authentication.getName();
        Optional<User> user = userRepository.findByUsername(email);
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("Пользователь с таким email не найден");

    }

    @Override
    public User findUserByPhone(String phone) {
        return userRepository.findByPhoneNumber((phone)).orElseThrow(
                () -> new RuntimeException("Пользователь с таким номером не найден")
        );
    }

    @Override
    public User changeProfile(UserDTO userDTO) {
        User user = getCurrentUser();
        if (userDTO.getName() != null){user.setName(userDTO.getName());}
        if (userDTO.getSurname() != null){user.setSurname(userDTO.getSurname());}
        if (userDTO.getPatronymic() != null){user.setPatronymic(userDTO.getPatronymic());}
        if (userDTO.getAvatarUrl() != null){user.setAvatarUrl(userDTO.getAvatarUrl());}
        return userRepository.save(user);
    }
}
