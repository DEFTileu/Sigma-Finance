package kz.javazhan.sigma_finance.domain.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String patronymic;
    private String avatarUrl;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static class UserDTOBuilder {

        private Long id;
        private String username;
        private String name;
        private String surname;
        private String patronymic;
        private String avatarUrl;
        private String phoneNumber;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;


        public UserDTOBuilder builder() {
            return this;
        }

        public UserDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserDTOBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserDTOBuilder setPatronymic(String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public UserDTOBuilder setAvatarUrl(String url) {
            this.avatarUrl = url;
            return this;
        }

        public UserDTOBuilder setPhoneNumber(String number) {
            this.phoneNumber = number;
            return this;
        }

        public UserDTOBuilder setCreatedAt(LocalDateTime time) {
            this.createdAt = time;
            return this;
        }

        public UserDTOBuilder setUpdatedAt(LocalDateTime time) {
            this.updatedAt = time;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(id, username, name, surname, patronymic, avatarUrl, phoneNumber, createdAt, updatedAt);
        }
    }
}