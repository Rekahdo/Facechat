package com.rekahdo.facechat._services;

import com.rekahdo.facechat._dtos.ChatDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	public ResponseEntity<?> sendChat(Long userId, ChatDto dto) {

		return null;
	}

	public ResponseEntity<?> getChats(Long userId) {

		return null;
	}

	public ResponseEntity<?> getChat(Long userId, Long chatId) {

		return null;
	}

	public ResponseEntity<?> editChat(Long userId, Long chatId, ChatDto dto) {

		return null;
	}

	public ResponseEntity<?> deleteChat(Long userId, Long chatId) {

		return null;
	}
}
