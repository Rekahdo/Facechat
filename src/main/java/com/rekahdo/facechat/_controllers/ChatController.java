package com.rekahdo.facechat._controllers;

import com.rekahdo.facechat._dtos.entities.ChatDto;
import com.rekahdo.facechat._dtos.paginations.ChatPageRequestDto;
import com.rekahdo.facechat._services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users/{userId}/chats")
public class ChatController {

	@Autowired
	private ChatService service;

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId)")
	@PostMapping(path = "")
	public ResponseEntity<?> sendChat(@PathVariable Long userId, @Valid @RequestBody ChatDto dto) {
		return service.sendChat(userId, dto);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('MODERATOR')")
	@GetMapping(path = "")
	public ResponseEntity<?> getChats(@PathVariable Long userId, @ModelAttribute ChatPageRequestDto dto,
									  @RequestParam(required = false, defaultValue = "0") Long friendId) {
		return service.getChats(userId, dto, friendId);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId) OR hasRole('ADMIN') OR hasRole('MODERATOR')")
	@GetMapping(path = "/{chatId}")
	public ResponseEntity<?> getChat(@PathVariable Long userId, @PathVariable Long chatId) {
		return service.getChat(userId, chatId);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId)")
	@PatchMapping(path = "/{chatId}")
	public ResponseEntity<?> editChat(@PathVariable Long userId, @PathVariable Long chatId,
									  @Valid @RequestBody ChatDto dto) {
		return service.editChat(userId, chatId, dto);
	}

	@PreAuthorize("@appUserSecurity.isUserAuth(authentication, #userId)")
	@DeleteMapping(path = "/{chatId}")
	public ResponseEntity<?> deleteChat(@PathVariable Long userId, @PathVariable Long chatId) {
		return service.deleteChat(userId, chatId);
	}

}
