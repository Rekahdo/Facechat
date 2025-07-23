package com.rekahdo.facechat._mappers;

import com.rekahdo.facechat._dtos.ChatDto;
import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat._entities.Chat;
import com.rekahdo.facechat._entities.Friendship;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = AppUserMapper.class)
public interface ChatMapper {

	ChatDto toDto(Chat chat);
	Chat toEntity(ChatDto chatDto);
	List<ChatDto> toDtoList(List<Chat> chats);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntity(ChatDto source, @MappingTarget Chat target);

	@AfterMapping
	default void afterMappingToEntity(@MappingTarget Chat target, ChatDto source) {

	}

	@AfterMapping
	default void afterMappingToDto(@MappingTarget ChatDto target, Chat source) {
	}

}
