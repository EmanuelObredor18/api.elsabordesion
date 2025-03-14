package org.mlsoftware.api.elsabordesion.security.data.dto.output;

import java.util.Set;

import org.mlsoftware.api.elsabordesion.security.data.enums.RoleEnum;


public record UserOutputDTO(String username, Set<RoleEnum> roleNames) {
  
}
