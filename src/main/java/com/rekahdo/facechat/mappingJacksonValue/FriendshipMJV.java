package com.rekahdo.facechat.mappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rekahdo.facechat._dtos.AppUserDto;
import org.springframework.http.converter.json.MappingJacksonValue;

public final class FriendshipMJV {

	private static final FilterProvider FRIENDSHIP_FILTER = new SimpleFilterProvider()
			.addFilter("appUserDtoFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "username"))
			.addFilter("friendshipDtoFilter", SimpleBeanPropertyFilter.serializeAllExcept("friendId"));

	public static <T> MappingJacksonValue friendshipFilter(T dto) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dto);
		mappingJacksonValue.setFilters(FRIENDSHIP_FILTER);
		return mappingJacksonValue;
	}

}
