package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SameIdException extends Api_Exception {

	public SameIdException(Long id, Long id2) {
		super(String.format("ID '%d' AND ID %d CAN NOT BE THE SAME", id, id2), HttpStatus.CONFLICT);
	}

}