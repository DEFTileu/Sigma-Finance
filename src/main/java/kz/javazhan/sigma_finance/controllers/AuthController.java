package kz.javazhan.sigma_finance.controllers;

import kz.javazhan.sigma_finance.domain.DTOS.AuthRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.AuthResponseDTO;
import kz.javazhan.sigma_finance.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    ResponseEntity<AuthResponseDTO> registerUser(@RequestBody AuthRequestDTO request){
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponseDTO> loginUser(@RequestBody AuthRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
