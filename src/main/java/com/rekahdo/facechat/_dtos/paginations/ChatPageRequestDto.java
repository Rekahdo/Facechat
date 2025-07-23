package com.rekahdo.facechat._dtos.paginations;

public class ChatPageRequestDto extends PageRequestDto{

	public ChatPageRequestDto() {
		super(0, 100, false, "sentAt");
	}

}