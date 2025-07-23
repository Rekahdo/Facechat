package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ModificationException extends Api_Exception {

	public ModificationException(String message) {
		super(message, HttpStatus.NOT_ACCEPTABLE);
	}

}