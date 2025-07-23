package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class Api_Exception extends RuntimeException {

	private final HttpStatusCode statusCode;

	public Api_Exception(String message,  HttpStatusCode statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

}
