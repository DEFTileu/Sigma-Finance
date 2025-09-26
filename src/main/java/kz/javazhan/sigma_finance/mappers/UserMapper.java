package kz.javazhan.sigma_finance.mappers;

import kz.javazhan.sigma_finance.domain.DTOS.AuthRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.UserDTO;
import org.mapstruct.Mapper;
import kz.javazhan.sigma_finance.domain.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User toUser(AuthRequestDTO request);

    UserDTO toUserDTO(User user);
}
