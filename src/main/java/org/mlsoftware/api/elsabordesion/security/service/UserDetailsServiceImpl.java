package org.mlsoftware.api.elsabordesion.security.service;

import org.mlsoftware.api.elsabordesion.base.BaseService;
import org.mlsoftware.api.elsabordesion.security.data.dto.input.UserInputDTO;
import org.mlsoftware.api.elsabordesion.security.data.dto.output.UserOutputDTO;
import org.mlsoftware.api.elsabordesion.security.data.entity.Identificable;
import org.mlsoftware.api.elsabordesion.security.data.entity.UserEntity;
import org.mlsoftware.api.elsabordesion.security.data.repository.UserRepository;
import org.mlsoftware.api.elsabordesion.security.exception.DataExistsException;
import org.mlsoftware.api.elsabordesion.security.exception.NoDataExistsException;
import org.mlsoftware.api.elsabordesion.security.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, BaseService<UserInputDTO, UserOutputDTO, Long> {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

		userEntity.getRoles()
				.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

		userEntity.getRoles().stream()
				.forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getRoleEnum().name())));

		return new User(userEntity.getUsername(),
				userEntity.getPassword(),
				true,
				true,
				true,
				true,
				authorityList);
	}

	@Override
	public String save(UserInputDTO dto) {

		if (repository.existsByUsername(dto.username())) {
			throw new DataExistsException("The user already exists");
		}

		UserEntity userEntity = mapper.inputDTOtoEntity(dto);
		repository.save(userEntity);
		return "User saved successfully";
	}

	@Override
	public void deleteById(Long id) {
		if (!repository.existsById(id)) {
			throw new NoDataExistsException("No data associated with id: " + id);
		}

		repository.deleteById(id);
	}

	@Override
	public UserOutputDTO update(Identificable<Long> user) {
		if (!repository.existsById(user.getId())) {
			throw new NoDataExistsException("No data associated with id: " + user.getId());
		}
		
		return mapper.toOutputDTO(repository.save((UserEntity) user));
		
	}

	@Override
	public UserOutputDTO getById(Long id) {
		return mapper.toOutputDTO(repository.findById(id).orElseThrow(() -> new NoDataExistsException("No data associated with id: " + id)));
	}

	
}
