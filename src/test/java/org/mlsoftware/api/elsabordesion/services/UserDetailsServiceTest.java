package org.mlsoftware.api.elsabordesion.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mlsoftware.api.elsabordesion.security.data.dto.input.UserInputDTO;
import org.mlsoftware.api.elsabordesion.security.data.dto.output.UserOutputDTO;
import org.mlsoftware.api.elsabordesion.security.data.entity.Role;
import org.mlsoftware.api.elsabordesion.security.data.entity.UserEntity;
import org.mlsoftware.api.elsabordesion.security.data.enums.RoleEnum;
import org.mlsoftware.api.elsabordesion.security.data.repository.UserRepository;
import org.mlsoftware.api.elsabordesion.security.exception.DataExistsException;
import org.mlsoftware.api.elsabordesion.security.exception.NoDataExistsException;
import org.mlsoftware.api.elsabordesion.security.service.UserDetailsServiceImpl;
import org.mlsoftware.api.elsabordesion.security.util.mapper.UserMapper;
import org.mlsoftware.api.elsabordesion.security.util.mapper.UserMapperImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;  
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

  private UserInputDTO userInputDTO;
  private UserOutputDTO userOutputDTO;
  private UserEntity userEntity;

  @Spy
  private UserMapper userMapper = new UserMapperImpl();

  @Mock
  private UserRepository repository;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @BeforeEach
  public void setUp() {
    Set<RoleEnum> roleNames = Set.of(RoleEnum.ADMIN);
    Set<Role> roles = Set.of(new Role(1L, RoleEnum.ADMIN));

    userInputDTO = new UserInputDTO("user@example.com", "hashed-password", roleNames);
    userOutputDTO = new UserOutputDTO("user@example.com", roleNames);
    userEntity = UserEntity.builder()
        .id(1L)
        .username("user@example.com")
        .password("hashed-password")
        .roles(roles)
        .build();
  }

  @Test
  public void should_ReturnUserOutputDTO_When_UserIsSavedSuccessfully() {
    when(repository.existsByUsername(userInputDTO.username())).thenReturn(false);
    when(repository.save(any(UserEntity.class))).thenReturn(userEntity);

    assertEquals("User saved successfully", userDetailsServiceImpl.save(userInputDTO));
  }

  @Test
  public void should_ThrowDataExistsException_When_TryingToSaveExistingUser() {
    when(repository.existsByUsername(userInputDTO.username())).thenReturn(true);

    assertThrows(DataExistsException.class, () -> userDetailsServiceImpl.save(userInputDTO));
  }

  @Test
  public void should_RunNormally_When_UserIsDeleted() {
    long userId = 1L;
    when(repository.existsById(userId)).thenReturn(true);

    userDetailsServiceImpl.deleteById(userId);

    verify(repository).deleteById(userId);
  }

  @Test
  public void should_ThrowNoDataExistsException_When_TryingToDeleteNonExistingUser() {
    long userId = 1L;
    when(repository.existsById(userId)).thenReturn(false);

    assertThrows(NoDataExistsException.class, () -> userDetailsServiceImpl.deleteById(userId));
  }

  @Test
  public void should_ReturnUpdatedUserOutputDTO_When_UpdatingValidUser() {
    when(repository.existsById(userEntity.getId())).thenReturn(true);
    when(repository.save(any(UserEntity.class))).thenReturn(userEntity);

    assertEquals(userOutputDTO, userDetailsServiceImpl.update(userEntity));
  }

  @Test
  public void should_ThrowNoDataExistsException_When_TryingToUpdateNonExistingUser() {
    when(repository.existsById(userEntity.getId())).thenReturn(false);

    assertThrows(NoDataExistsException.class, () -> userDetailsServiceImpl.update(userEntity));
  }

  @Test
  public void should_ReturnUserOutputDTO_When_RequestingExistingUserById() {
    long userId = 1L;
    when(repository.findById(userId)).thenReturn(Optional.of(userEntity));

    assertEquals(userOutputDTO, userDetailsServiceImpl.getById(userId));
  }

  @Test
  public void should_ThrowNoDataExistsException_When_RequestingNonExistingUserById() {
    long userId = 1L;
    when(repository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(NoDataExistsException.class, () -> userDetailsServiceImpl.getById(userId));
  }
}
