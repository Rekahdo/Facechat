package com.rekahdo.facechat._services;

import com.rekahdo.facechat._controllers.ChatController;
import com.rekahdo.facechat._dtos.AppUserDto;
import com.rekahdo.facechat._dtos.ChatDto;
import com.rekahdo.facechat._dtos.paginations.ChatPageRequestDto;
import com.rekahdo.facechat._dtos.paginations.PageRequestDto;
import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat._entities.Chat;
import com.rekahdo.facechat._mappers.AppUserMapper;
import com.rekahdo.facechat._mappers.ChatMapper;
import com.rekahdo.facechat._repository.AppUserRepository;
import com.rekahdo.facechat._repository.ChatRepository;
import com.rekahdo.facechat._repository.FriendshipRepository;
import com.rekahdo.facechat.enums.ChatStatus;
import com.rekahdo.facechat.enums.ContentType;
import com.rekahdo.facechat.exceptions.classes.ChatNotFoundException;
import com.rekahdo.facechat.exceptions.classes.EmptyListException;
import com.rekahdo.facechat.exceptions.classes.UserIdNotFoundException;
import com.rekahdo.facechat.mappingJacksonValue.ChatMJV;
import com.rekahdo.facechat.utilities.PageRequestUriBuilder;
import com.rekahdo.facechat.utilities.SortChatDtoByDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.rekahdo.facechat.enums.ChatStatus.DELIVERED;
import static com.rekahdo.facechat.enums.ChatStatus.SEEN;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ChatService {

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private ChatMapper mapper;

	@Autowired
	private AppUserMapper userMapper;

	@Autowired
	private ChatRepository repo;

	@Autowired
	private PageRequestUriBuilder<ChatDto> pageLinkBuilder;

	@Autowired
	private FriendshipRepository friendshipRepository;

	public ResponseEntity<?> sendChat(Long userId, ChatDto dto) {
		if (!appUserRepository.existsById(dto.getFriendId()))
			throw new UserIdNotFoundException(dto.getFriendId());

		if (dto.getSentAt() == null) dto.setSentAt(Instant.now());
		if (dto.getStatus() == null) dto.setStatus(ChatStatus.SENT);
		dto.setContentType(ContentType.TEXT);

		Chat chat = mapper.toEntity(dto);
		chat.setSender(new AppUser(userId));
		chat.setReceiver(new AppUser(dto.getFriendId()));
		repo.save(chat);
		return ResponseEntity.ok().build();
	}

	public <T> ResponseEntity<?> getChats(Long userId, ChatPageRequestDto dto, Long friendId) {
		boolean groupChats =  friendId == 0;
		List<Chat> chats = (groupChats ? repo.findAllByUserId(userId)
				: repo.findAllByUserIdAndFriendId(userId, friendId));
		if(chats == null || chats.isEmpty()) throw new EmptyListException();

		Page<ChatDto> pageDto = (groupChats ? pageLinkBuilder.getPagedList(dto, groupChats(chats, userId))
				: pageLinkBuilder.getPagedList(dto, chats).map(chat -> setFriend(chat, userId)));
		PagedModel<ChatDto> pagedModel = pageLinkBuilder.getPagedModel(dto, pageDto, methodOn(ChatController.class).getChats(userId, dto, friendId));
		return ResponseEntity.ok(groupChats ? ChatMJV.chatGroupFilter(pagedModel) : ChatMJV.chatFilter(pagedModel));
	}

	private List<ChatDto> groupChats(List<Chat> chats, Long userId) {
		Map<String, List<ChatDto>> groupedChatDtos = chats.stream()
				.map(chat -> setFriend(chat, userId))
				.sorted(new SortChatDtoByDate())
				.collect(Collectors.groupingBy(chatDto -> chatDto.getFriend().getUsername()));

		List<ChatDto> lastChat = new ArrayList<>();
		groupedChatDtos.forEach((username, chatDtos) -> {
			ChatDto recent = chatDtos.getFirst();
			long unread = chatDtos.stream().filter(chatDto -> !chatDto.getStatus().equals(SEEN)).count();
			boolean isReceiverView = userId.equals(recent.getReceiver().getId());
			recent.setUnread(isReceiverView ? unread : 0);
			lastChat.add(recent);
		});
		return lastChat;
	}

	public ResponseEntity<?> getChat(Long userId, Long chatId) {
		Optional<Chat> optional = repo.findByUserIdAndChatId(userId, chatId);
		if(optional.isEmpty()) throw new ChatNotFoundException(chatId);

		Chat chat = optional.get();
		ChatDto dto = mapper.toDto(chat);
		dto.setFriend(getFriendDto(chat, userId));
		return ResponseEntity.ok(ChatMJV.chatFilter(dto));
	}

	public ResponseEntity<?> editChat(Long userId, Long chatId, ChatDto dto) {
		Optional<Chat> optional = repo.findByUserIdAndChatId(userId, chatId);
		if(optional.isEmpty()) throw new ChatNotFoundException(chatId);

		Chat chat = optional.get();
		boolean isSender = isSender(chat, userId);
		boolean isReceiver = isReceiver(chat, userId);

		if(dto.getStatus() != null){
			if(isSender && List.of(DELIVERED, SEEN).contains(dto.getStatus()))
				throw new AccessDeniedException("Only receiver can edit chat status to [DELIVERED, SEEN]");

			if (dto.getStatus().equals(DELIVERED) || (chat.getDeliveredAt() != null && dto.getStatus().equals(SEEN)))
				dto.setDeliveredAt(Instant.now());

			if (dto.getStatus().equals(SEEN))
				dto.setSeenAt(Instant.now());
		}

		if(isReceiver && !dto.getContent().equals(chat.getContent()))
			throw new AccessDeniedException("Only sender can edit chat content to a different text");
		
		mapper.updateEntity(dto, chat);
		repo.save(chat);
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<?> deleteChat(Long userId, Long chatId) {
		repo.deleteByUserIdAndId(userId, chatId);
		return ResponseEntity.ok().build();
	}

	private AppUserDto getFriendDto(Chat chat, Long userId){
		AppUser friend = chat.getSender().getId().equals(userId)
				? chat.getReceiver() : chat.getSender();
		return userMapper.toDto(friend);
	}

	private ChatDto setFriend(Chat chat, Long userId){
		ChatDto chatDto = mapper.toDto(chat);
		chatDto.setFriend(getFriendDto(chat, userId));
		return chatDto;
	}

	private boolean isSender(Chat chat, Long userId){
		return chat.getSender().getId().equals(userId);
	}

	private boolean isReceiver(Chat chat, Long userId){
		return chat.getReceiver().getId().equals(userId);
	}

}
