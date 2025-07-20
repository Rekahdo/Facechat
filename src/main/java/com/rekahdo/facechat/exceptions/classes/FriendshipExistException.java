package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FriendshipExistException extends Api_Exception {

	public FriendshipExistException(Long id) {
		super(String.format("FRIENDSHIP EXIST WITH USER ID '%d'", id), HttpStatus.CONFLICT, Error.UNKNOWN_VIOLATION);
	}

}