package com.rekahdo.facechat._dtos;

import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat.enums.FriendshipStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FriendshipDto extends EntityDto<FriendshipDto> {

	@Enumerated(EnumType.STRING)
	private FriendshipStatus status;
	private Long senderId;
	private AppUser receiverId;

}
