package org.mlsoftware.api.elsabordesion.security.data.dto.input;

import java.util.Set;

import org.mlsoftware.api.elsabordesion.security.data.enums.RoleEnum;

public record UserInputDTO(String username, String password, Set<RoleEnum> roleNames) {
  
}
