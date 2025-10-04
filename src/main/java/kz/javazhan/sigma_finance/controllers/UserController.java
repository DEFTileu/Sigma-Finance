package kz.javazhan.sigma_finance.controllers;

import kz.javazhan.sigma_finance.domain.DTOS.UserDTO;
import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.mappers.UserMapper;
import kz.javazhan.sigma_finance.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        User user =  userService.getCurrentUser();
        UserDTO dto = userMapper.toUserDTO(user);
        return ResponseEntity.ok(dto);
    }


    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserDTO userDTO) {
        UserDTO dto = userMapper.toUserDTO(userService.changeProfile(userDTO));
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/profile/avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody HashMap<String, String> request) {
        UserDTO userDto = new UserDTO();
        userDto.setAvatarUrl(request.get("url"));
        log.info("Received avatar URL: {}", request.get("url"));
        UserDTO dto = userMapper.toUserDTO(userService.changeProfile(userDto));
        log.info("Returning updated user DTO: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/check-phone")
    public ResponseEntity<?> checkPhone(@RequestBody HashMap<String, String> request) {
        User user =  userService.findUserByPhone(request.get("phone"));


        HashMap<String, String> response = new HashMap<>();
        response.put("id", user.getId().toString());
        response.put("name", user.getName());
        response.put("surname", user.getSurname());
        response.put("avatar", user.getAvatarUrl());
        return ResponseEntity.ok(response);
    }
}
