package com.rekahdo.facechat._services;

import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat._entities.Friendship;
import com.rekahdo.facechat._mappers.FriendshipMapper;
import com.rekahdo.facechat._repository.AppUserRepository;
import com.rekahdo.facechat._repository.FriendshipRepository;
import com.rekahdo.facechat.enums.FriendshipStatus;
import com.rekahdo.facechat.exceptions.classes.UserIdNotFoundException;
import com.rekahdo.facechat.exceptions.classes.UsernameExistException;
import com.rekahdo.facechat.mappingJacksonValue.FriendshipMJV;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FriendshipService {

	@Autowired
	private FriendshipRepository repo;

	@Autowired
	private FriendshipMapper mapper;

	@Autowired
	private AppUserRepository appUserRepository;

	public ResponseEntity<?> addFriendship(Long senderId, FriendshipDto dto) {
		if (!appUserRepository.existsById(dto.getReceiverId()))
			throw new UserIdNotFoundException(dto.getReceiverId());

		dto.setId(null);
		dto.setStatus(FriendshipStatus.PENDING);
		dto.setCreatedAt(Instant.now());
		dto.setSenderId(senderId);

		Friendship friendship = mapper.toEntity(dto);
//		dto = mapper.toDto(repo.save(friendship));
		return ResponseEntity.ok(FriendshipMJV.publicFilter(repo.save(friendship)));
	}

	public ResponseEntity<?> getFriendships(Long userId, String status) {

		return null;
	}

	public ResponseEntity<?> getFriendship(Long userId, Long friendId) {

		return null;
	}

	public ResponseEntity<?> editFriendship(Long userId, Long friendId, FriendshipDto dto) {

		return null;
	}

	public ResponseEntity<?> deleteFriendship(Long userId, Long friendId) {

		return null;
	}
}
