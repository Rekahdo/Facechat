package com.rekahdo.facechat._controllers;

import com.rekahdo.facechat._dtos.AppUserDto;
import com.rekahdo.facechat._dtos.PageRequestDto;
import com.rekahdo.facechat._services.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableMethodSecurity
@RequestMapping(path = "/api/v1/users")
public class AppUserController {

	@Autowired
	private AppUserService service;

	@PostMapping(path = "/register", consumes = "application/json")
	public ResponseEntity<?> createUser(@Valid @RequestBody AppUserDto dto) {
		return service.createUser(dto);
	}

	@PostMapping(path = "/login", consumes = "application/json")
	public ResponseEntity<?> loginUser(@RequestBody AppUserDto dto) {
		return service.loginUser(dto);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@GetMapping("")
	public ResponseEntity<?> getUsers(@ModelAttribute PageRequestDto dto) {
		return service.getUsers(dto);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('MODERATOR')")
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUser(@PathVariable Long userId) {
		return service.getUser(userId);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('EDITOR')")
	@PutMapping(path = "/{userId}", consumes = "application/json")
	public ResponseEntity<?> putUser(@PathVariable Long userId, @Valid @RequestBody AppUserDto dto) {
		return service.putUser(userId, dto);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('EDITOR')")
	@PatchMapping(path = "/{userId}", consumes = "application/json")
	public ResponseEntity<?> patchUser(@PathVariable Long userId, @Valid @RequestBody AppUserDto dto) {
		return service.patchUser(userId, dto);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN')")
	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		return service.deleteUser(userId);
	}

}
