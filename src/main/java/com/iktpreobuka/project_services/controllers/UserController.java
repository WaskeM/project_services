package com.iktpreobuka.project_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project_services.entities.UserEntity;
import com.iktpreobuka.project_services.enumerations.EUserRole;
import com.iktpreobuka.project_services.repositories.UserRepository;

@RestController
@RequestMapping(path = "/project/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserEntity addNewUser(@RequestBody UserEntity newUser) {
		if (newUser == null) {
			return null;
		}

		if (newUser.getFirstName() == null || newUser.getLastName() == null || newUser.getUsername() == null
				|| newUser.getPassword() == null) {
			return null;
		}
		
		newUser.setRole(EUserRole.ROLE_CUSTOMER);
		return userRepository.save(newUser);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity updateUser) {
		UserEntity user = userRepository.findById(id).get();

		if (user == null || updateUser == null) {
			return null;
		}

		if (updateUser.getFirstName() != null || !updateUser.getFirstName().equals(" ") || !updateUser.getFirstName().equals("")) {
			user.setFirstName(updateUser.getFirstName());
		}
		if (updateUser.getLastName() != null || !updateUser.getLastName().equals(" ") || !updateUser.getLastName().equals("")) {
			user.setLastName(updateUser.getLastName());
		}

		if (updateUser.getEmail() != null || !updateUser.getLastName().equals(" ") || !updateUser.getLastName().equals("")) {
			user.setEmail(updateUser.getEmail());
		}
		return userRepository.save(user);

	}

	@RequestMapping(method = RequestMethod.PUT, value = "change/{id}/role/{role}")
	public UserEntity changeUserRole(@PathVariable Integer id, @PathVariable String role) {
		UserEntity user = userRepository.findById(id).get();

		if (user == null) {
			return null;
		}
		EUserRole userRole = EUserRole.valueOf(role);
		user.setRole(userRole);

		return userRepository.save(user);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "changePassword/{id}")
	public UserEntity changePassword(@PathVariable Integer id, @RequestParam("newPassword") String newPassword,
			@RequestParam("oldPassword") String oldPassword) {
		UserEntity user = userRepository.findById(id).get();

		if (user == null) {
			return null;
		}

		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
		}

		return userRepository.save(user);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public UserEntity deleteUser(@PathVariable Integer id) {
		UserEntity user = userRepository.findById(id).get();

		if (user == null) {
			return null;
		}
		userRepository.deleteById(id);
		return user;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public UserEntity findByUserById(@PathVariable Integer id) {
		return userRepository.findById(id).get();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/by-username/{username}")
	public UserEntity findUserByUsername(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}
}
