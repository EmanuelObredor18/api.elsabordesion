package org.mlsoftware.api.elsabordesion.security.util.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mlsoftware.api.elsabordesion.security.data.dto.input.UserInputDTO;
import org.mlsoftware.api.elsabordesion.security.data.dto.output.UserOutputDTO;
import org.mlsoftware.api.elsabordesion.security.data.entity.Role;
import org.mlsoftware.api.elsabordesion.security.data.entity.UserEntity;
import org.mlsoftware.api.elsabordesion.security.data.enums.RoleEnum;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roles", target = "roleNames")
    UserOutputDTO toOutputDTO(UserEntity user);

    // Ignore property warning. The ID is provided by JPA.
    UserEntity inputDTOtoEntity(UserInputDTO userDTO);

    default Set<RoleEnum> mapRoles(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                    .map(Role::getRoleEnum) // Suponiendo que Role tiene un método getName() que devuelve RoleEnum
                    .collect(Collectors.toSet());
    }
}
