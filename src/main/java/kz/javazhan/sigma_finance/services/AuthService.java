package kz.javazhan.sigma_finance.services;

import kz.javazhan.sigma_finance.domain.DTOS.AuthRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.AuthResponseDTO;

 public interface AuthService {
     AuthResponseDTO register(AuthRequestDTO request);

     AuthResponseDTO login(AuthRequestDTO request);

     String logout(String refreshToken);
}
