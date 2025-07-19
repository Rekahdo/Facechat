package com.rekahdo.facechat._services;

import com.rekahdo.facechat._dtos.FriendshipDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {


	public ResponseEntity<?> getFriendships(Long userId, String status) {

		return null;
	}

	public ResponseEntity<?> getFriendship(Long userId, Long friendId) {

		return null;
	}

	public ResponseEntity<?> addFriendship(Long userId, Long friendId) {

		return null;
	}

	public ResponseEntity<?> editFriendship(Long userId, Long friendId, FriendshipDto dto) {

		return null;
	}

	public ResponseEntity<?> deleteFriendship(Long userId, Long friendId) {

		return null;
	}
}
