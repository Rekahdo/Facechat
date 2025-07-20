package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OnlyReceiverException extends Api_Exception {

	public OnlyReceiverException(String message) {
		super(message, HttpStatus.UNAUTHORIZED, Error.UNKNOWN_VIOLATION);
	}

}