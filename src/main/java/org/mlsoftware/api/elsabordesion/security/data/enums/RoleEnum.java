package org.mlsoftware.api.elsabordesion.security.data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleEnum {
    ADMIN, ORDER_MANAGER, DEV, CLIENT;

    @JsonValue
    public String getValue() {
        return name(); // Devuelve el nombre tal cual
    }

    @JsonCreator
    public static RoleEnum fromString(String value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Valor no válido para RoleEnum: " + value);
    }
}
