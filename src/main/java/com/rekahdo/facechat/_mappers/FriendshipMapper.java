package com.rekahdo.facechat._mappers;

import com.rekahdo.facechat._dtos.entities.FriendshipDto;
import com.rekahdo.facechat._entities.Friendship;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {

	FriendshipDto toDto(Friendship friendship);
	Friendship toEntity(FriendshipDto friendshipDto);
	List<FriendshipDto> toDtoList(List<Friendship> friendships);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateEntity(FriendshipDto source, @MappingTarget Friendship target);

	@AfterMapping
	default void afterMappingToEntity(@MappingTarget Friendship target, FriendshipDto source) {

	}

	@AfterMapping
	default void afterMappingToDto(@MappingTarget FriendshipDto target, Friendship source) {

	}

}
