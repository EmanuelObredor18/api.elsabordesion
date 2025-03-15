package org.mlsoftware.api.elsabordesion.security.data.dto.input;

import java.util.Set;

import org.mlsoftware.api.elsabordesion.security.annotations.ValidListEnum;
import org.mlsoftware.api.elsabordesion.security.data.enums.RoleEnum;

public record UserInputDTO(
    String username,
    String password,
    @ValidListEnum(enumClass = RoleEnum.class, message = "El conjunto contiene un valor no válido") Set<RoleEnum> roleNames) {

}
