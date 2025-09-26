package kz.javazhan.sigma_finance.services;

import kz.javazhan.sigma_finance.domain.DTOS.AuthRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.AuthResponseDTO;

public interface AuthService {
    public AuthResponseDTO register(AuthRequestDTO request);

    public AuthResponseDTO login(AuthRequestDTO request);

    public String logout(String refreshToken);
}
