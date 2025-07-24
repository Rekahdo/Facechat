package com.rekahdo.facechat._dtos.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.rekahdo.facechat.enums.ChatStatus;
import com.rekahdo.facechat.enums.ContentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
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
@JsonFilter("chatDtoFilter")
public class ChatDto extends EntityDto<ChatDto>{

	@NotNull
	@NotBlank
	private String content;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ContentType contentType;

	@Enumerated(EnumType.STRING)
	private ChatStatus status;

	private Instant sentAt;

	private Instant deliveredAt;

	private Instant seenAt;

	private long unread;

	@NotNull(message = "Friend id must contain a user id")
	private Long friendId;

	private AppUserDto sender;
	private AppUserDto receiver;
	private AppUserDto friend;
	private Long friendshipId;

	public ChatDto(String content, ChatStatus status, Instant sentAt, Instant deliveredAt, Instant seenAt, Long friendId, Long friendshipId) {
		this.content = content;
		this.status = status;
		this.sentAt = sentAt;
		this.deliveredAt = deliveredAt;
		this.seenAt = seenAt;
		this.friendId = friendId;
		this.friendshipId = friendshipId;
	}

	public ChatDto(String content, ChatStatus status, Instant sentAt, Instant deliveredAt, Instant seenAt, Long friendId) {
		this.content = content;
		this.status = status;
		this.sentAt = sentAt;
		this.deliveredAt = deliveredAt;
		this.seenAt = seenAt;
		this.friendId = friendId;
	}
}
