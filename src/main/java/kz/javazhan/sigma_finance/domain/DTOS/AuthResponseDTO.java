package kz.javazhan.sigma_finance.domain.DTOS;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    UserDTO user;
    String accessToken;
    String refreshToken;
}
