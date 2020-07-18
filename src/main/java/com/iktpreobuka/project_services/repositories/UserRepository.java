package com.iktpreobuka.project_services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project_services.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	UserEntity findByEmail(String email);

	UserEntity findByUsername(String name);
	
	//List<UserEntity> findDistinctUserEntityByFirstNameOrLastName();
	
	List<UserEntity> findUserEntityDistinctByFirstNameOrLastName(String firstName, String lastName);
}
