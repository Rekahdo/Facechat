package com.rekahdo.facechat._mappers;

import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat._entities.Friendship;
import com.rekahdo.facechat.utilities.AuthUser;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = AppUserMapper.class)
public interface FriendshipMapper {

	FriendshipDto toDto(Friendship friendship);
	Friendship toEntity(FriendshipDto friendshipDto);
	List<FriendshipDto> toDtoList(List<Friendship> friendships);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntity(FriendshipDto source, @MappingTarget Friendship target);

	@AfterMapping
	default void afterMappingToEntity(@MappingTarget Friendship target, FriendshipDto source) {
		target.setSender(new AppUser(source.getSenderId()));
		target.setReceiver(new AppUser(source.getReceiverId()));
	}

	@AfterMapping
	default void afterMappingToDto(@MappingTarget FriendshipDto target, Friendship source) {
		target.setFriendRequestSender(AuthUser.USERNAME().equals(source.getSender().getUsername()));
	}

}
