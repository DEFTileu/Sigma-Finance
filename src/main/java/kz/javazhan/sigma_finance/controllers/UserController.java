package kz.javazhan.sigma_finance.controllers;

import kz.javazhan.sigma_finance.domain.entities.User;
import kz.javazhan.sigma_finance.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

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
