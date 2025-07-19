package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserIdNotFoundException extends Api_Exception {

	private final Long userId;

	public UserIdNotFoundException(Long userId) {
		super(String.format("USER WITH ID '%d' NOT FOUND", userId), HttpStatus.NOT_FOUND, Error.USER_ID_NOT_FOUND_VIOLATION);
		this.userId = userId;
	}

}