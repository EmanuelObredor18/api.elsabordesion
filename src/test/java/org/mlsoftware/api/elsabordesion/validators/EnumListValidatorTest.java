package org.mlsoftware.api.elsabordesion.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mlsoftware.api.elsabordesion.security.data.dto.input.UserInputDTO;
import org.mlsoftware.api.elsabordesion.security.data.enums.RoleEnum;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class EnumListValidatorTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void givenValidEnumSet_whenValidating_thenNoValidationErrors() {
    UserInputDTO dto = new UserInputDTO("user", "password", Set.of(RoleEnum.ADMIN, RoleEnum.CLIENT));
    assertTrue(validator.validate(dto).isEmpty(), "No debe haber errores de validación");
  }

  @Test
  void givenEnumSetWithNullValue_whenValidating_thenValidationErrorOccurs() {
    Set<RoleEnum> invalidRoles = new HashSet<>();
    invalidRoles.add(null); // Simula un valor inválido

    UserInputDTO dto = new UserInputDTO("user", "password", invalidRoles);
    var violations = validator.validate(dto);

    // Imprimir los mensajes de error para depuración
    violations.forEach(violation -> System.out.println(violation.getMessage()));

    assertFalse(violations.isEmpty(), "Debe haber un error, pero no se encontró ninguno.");
  }

  @Test
  void givenEmptyEnumSet_whenValidating_thenValidationErrorOccurs() {
    UserInputDTO dto = new UserInputDTO("user", "password", Set.of());
    assertFalse(validator.validate(dto).isEmpty(), "Debe haber un error si el set está vacío");
  }

  @Test
  void givenValidRole_whenFromString_thenReturnsEnum() {
    assertEquals(RoleEnum.ADMIN, RoleEnum.fromString("ADMIN"));
    assertEquals(RoleEnum.DEV, RoleEnum.fromString("DEV"));
    assertEquals(RoleEnum.ORDER_MANAGER, RoleEnum.fromString("ORDER_MANAGER"));
    assertEquals(RoleEnum.CLIENT, RoleEnum.fromString("CLIENT"));
  }

  @Test
  void givenInvalidRole_whenFromString_thenThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> RoleEnum.fromString("INVALID_ROLE"));
    assertThrows(IllegalArgumentException.class, () -> RoleEnum.fromString(""));
    assertThrows(IllegalArgumentException.class, () -> RoleEnum.fromString(null));
  }

}
