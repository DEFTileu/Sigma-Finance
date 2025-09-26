package kz.javazhan.sigma_finance.domain.DTOS;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    @Email
    /// I am used to email as username
    private String username;

    private String password;

    private String name;

    private String surname;

    private String patronymic;

    private String phoneNumber;
}
