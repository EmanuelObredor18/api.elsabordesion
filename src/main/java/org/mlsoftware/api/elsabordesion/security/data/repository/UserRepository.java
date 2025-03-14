package org.mlsoftware.api.elsabordesion.security.data.repository;

import org.mlsoftware.api.elsabordesion.security.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
	boolean existsByUsername(String username);
}
