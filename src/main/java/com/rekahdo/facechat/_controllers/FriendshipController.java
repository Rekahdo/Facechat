package com.rekahdo.facechat._controllers;

import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._services.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users/{userId}/friends")
public class FriendshipController {

	@Autowired
	private FriendshipService service;

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId)")
	@PostMapping(path = "", consumes = "application/json")
	public ResponseEntity<?> addFriendship(@PathVariable Long userId, @Valid @RequestBody FriendshipDto dto) {
		return service.addFriendship(userId, dto);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('MODERATOR')")
	@GetMapping(path = "")
	public ResponseEntity<?> getFriendships(@PathVariable Long userId,
			@RequestParam(defaultValue = "ACCEPTED", required = false) String status) {
		return service.getFriendships(userId, status);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('MODERATOR')")
	@GetMapping(path = "/{friendId}")
	public ResponseEntity<?> getFriendship(@PathVariable Long userId, @PathVariable Long friendId) {
		return service.getFriendship(userId, friendId);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId)")
	@PatchMapping(path = "/{friendId}", consumes = "application/json")
	public ResponseEntity<?> editFriendship(@PathVariable Long userId, @PathVariable Long friendId,
			@Valid @RequestBody FriendshipDto dto) {
		return service.editFriendship(userId, friendId, dto);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId)")
	@DeleteMapping(path = "/{friendId}")
	public ResponseEntity<?> deleteFriendship(@PathVariable Long userId, @PathVariable Long friendId) {
		return service.deleteFriendship(userId, friendId);
	}

}
