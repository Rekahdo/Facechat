package com.rekahdo.facechat._dtos;

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

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatDto extends EntityDto<ChatDto>{

	@NotNull
	@NotBlank
	private String content;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ContentType contentType;

	private LocalDateTime sentTime;

	private LocalDateTime deliveredTime;

	private LocalDateTime seenTime;

	@Enumerated(EnumType.STRING)
	private ChatStatus status;

	private Long friendshipId;

	private Long senderId;

	private Long receiverId;

}
