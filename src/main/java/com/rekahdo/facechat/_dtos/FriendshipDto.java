package com.rekahdo.facechat._dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.rekahdo.facechat.enums.FriendshipStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonFilter("friendshipDtoFilter")
public class FriendshipDto extends EntityDto<FriendshipDto> {

	private Long id;

	@Enumerated(EnumType.STRING)
	private FriendshipStatus status;

	private boolean isFriendRequestSender;

	private Long senderId;
	private AppUserDto sender;

	@NotNull(message = "Receiver id must contain a user id")
	private Long receiverId;
	private AppUserDto receiver;

	private Instant createdAt;

	public FriendshipDto(Long receiverId) {
		this.receiverId = receiverId;
	}

}
