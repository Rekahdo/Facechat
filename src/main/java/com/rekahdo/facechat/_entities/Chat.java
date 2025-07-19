package com.rekahdo.facechat._entities;

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

	private ContentType contentType;

	private LocalDateTime sentTime;

	private LocalDateTime deliveredTime;

	private LocalDateTime seenTime;

	private ChatStatus status;

	@ManyToOne
	@JoinColumn(name = "friendship_id")
	@JsonIgnore
	private Friendship friendship;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	@JsonIgnore
	private AppUser sender;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	@JsonIgnore
	private AppUser receiver;

}
