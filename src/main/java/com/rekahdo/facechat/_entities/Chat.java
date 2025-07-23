package com.rekahdo.facechat._entities;

import java.time.Instant;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.rekahdo.facechat.enums.ChatStatus;
import com.rekahdo.facechat.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "chats")
public class Chat {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@Enumerated(EnumType.STRING)
	private ContentType contentType;

	@Enumerated(EnumType.STRING)
	private ChatStatus status;

	private Instant sentAt;

	private Instant deliveredAt;

	private Instant seenAt;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private AppUser sender;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private AppUser receiver;

	@ManyToOne
	@JoinColumn(name = "friendship_id")
	private Friendship friendship;

}
