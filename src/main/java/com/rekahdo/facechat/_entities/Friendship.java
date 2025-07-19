package com.rekahdo.facechat._entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.rekahdo.facechat.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "friendships")
public class Friendship {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private FriendshipStatus status;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	@JsonIgnore
	private AppUser sender;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	@JsonIgnore
	private AppUser receiver;

	private Instant createdAt;

	@OneToMany(mappedBy = "friendship", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Chat> chats = new ArrayList<>();

}
