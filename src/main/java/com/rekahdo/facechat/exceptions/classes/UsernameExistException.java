package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class UsernameExistException extends Api_Exception {

	private final String username;

	public UsernameExistException(String username) {
		super(String.format("USER WITH NAME '%s' EXIST", username), HttpStatus.FOUND, Error.USERNAME_EXIST_VIOLATION);
		this.username = username;
	}

}