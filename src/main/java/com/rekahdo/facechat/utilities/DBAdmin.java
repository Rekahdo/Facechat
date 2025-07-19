package com.rekahdo.facechat.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DBAdmin {

	@Value("${adminKey}")
	public String PASSWORD;

}
