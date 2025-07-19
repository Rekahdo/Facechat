package com.rekahdo.facechat._mappers;

import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat._entities.Friendship;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = AppUserMapper.class)
public interface FriendshipMapper {

	@Mappings({
			@Mapping(target = "sender", ignore = true),
			@Mapping(target = "receiver", ignore = true)
	})
	FriendshipDto toDto(Friendship friendship);

	@Mappings({
			@Mapping(target = "sender", ignore = true),
			@Mapping(target = "receiver", ignore = true)
	})
	Friendship toEntity(FriendshipDto friendshipDto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntity(FriendshipDto source, @MappingTarget Friendship target);

	@AfterMapping
	default void afterMappingToEntity(@MappingTarget Friendship target, FriendshipDto source) {
		target.setSender(new AppUser(source.getSenderId()));
		target.setReceiver(new AppUser(source.getReceiverId()));
	}

}
