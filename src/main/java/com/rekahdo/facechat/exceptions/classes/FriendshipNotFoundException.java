package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FriendshipNotFoundException extends Api_Exception {

	public FriendshipNotFoundException(Long userId, Long friendId) {
		super(String.format("USER ID '%d' AND USER ID '%d' ARE NOT FRIENDS", userId, friendId), HttpStatus.NOT_FOUND);
	}

}