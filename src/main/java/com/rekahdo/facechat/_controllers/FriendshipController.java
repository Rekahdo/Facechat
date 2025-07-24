package com.rekahdo.facechat._controllers;

import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._dtos.paginations.FriendshipPageRequestDto;
import com.rekahdo.facechat._dtos.paginations.PageRequestDto;
import com.rekahdo.facechat._services.FriendshipService;
import com.rekahdo.facechat.enums.FriendshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId)")
	@PatchMapping(path = "", consumes = "application/json")
	public ResponseEntity<?> editFriendship(@PathVariable Long userId, @Valid @RequestBody FriendshipDto dto) {
		return service.editFriendship(userId, dto);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('MODERATOR')")
	@GetMapping(path = "")
	public ResponseEntity<?> getFriendships(@PathVariable Long userId, @ModelAttribute FriendshipPageRequestDto dto,
			@RequestParam(defaultValue = "ACCEPTED,PENDING,BLOCKED", required = false) String status) {
		return service.getFriendships(userId, dto, status);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('MODERATOR')")
	@GetMapping(path = "/{friendId}")
	public ResponseEntity<?> getFriendship(@PathVariable Long userId, @PathVariable Long friendId) {
		return service.getFriendship(userId, friendId);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId)")
	@DeleteMapping(path = "/{friendId}")
	public ResponseEntity<?> deleteFriendship(@PathVariable Long userId, @PathVariable Long friendId) {
		return service.deleteFriendship(userId, friendId);
	}

}
