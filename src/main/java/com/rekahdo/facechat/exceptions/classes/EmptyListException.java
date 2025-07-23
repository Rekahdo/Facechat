package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import org.springframework.http.HttpStatus;

public class EmptyListException extends Api_Exception {

	public EmptyListException() {
		super("THE REQUEST RESOURCE HAS AN EMPTY LIST", HttpStatus.NO_CONTENT);
	}

}

