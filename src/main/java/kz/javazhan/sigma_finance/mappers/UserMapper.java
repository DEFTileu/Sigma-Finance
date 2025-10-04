package kz.javazhan.sigma_finance.mappers;

import kz.javazhan.sigma_finance.domain.DTOS.AuthRequestDTO;
import kz.javazhan.sigma_finance.domain.DTOS.UserDTO;
import org.mapstruct.Mapper;
import kz.javazhan.sigma_finance.domain.entities.User;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(AuthRequestDTO request);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "surname", source = "surname"),
        @Mapping(target = "username", source = "username"),
        @Mapping(target = "phoneNumber", source = "phoneNumber"),
        @Mapping(target = "avatarUrl", source = "avatarUrl"),
        @Mapping(target = "createdAt", source = "createdAt"),
        @Mapping(target = "updatedAt", source = "updatedAt")
    })
    UserDTO toUserDTO(User user);
}
