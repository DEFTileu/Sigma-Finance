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


    public UserDTO builder(){
        return this;
    }

    public UserDTO setId(Long id){
        this.id = id;
        return this;
    }

    public UserDTO setUsername(String username){
        this.username = username;
        return this;
    }

    public UserDTO setName(String name){
        this.name = name;
        return this;
    }

    public UserDTO setSurname(String surname){
        this.surname = surname;
        return this;
    }

    public UserDTO setPatronymic(String patronymic){
        this.patronymic = patronymic;
        return this;
    }

    public UserDTO setAvatarUrl(String url){
        this.avatarUrl = url;
        return this;
    }

    public UserDTO setPhoneNumber(String number){
        this.phoneNumber = number;
        return this;
    }

    public UserDTO setCreatedAt(LocalDateTime time){
        this.createdAt = time;
        return this;
    }

    public UserDTO setUpdatedAt(LocalDateTime time){
        this.updatedAt = time;
        return this;
    }

    public UserDTO build(){
        return this;
    }
}
