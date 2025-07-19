package com.rekahdo.facechat.mappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rekahdo.facechat._dtos.AppUserDto;
import org.springframework.http.converter.json.MappingJacksonValue;

public final class FriendshipMJV {

	private static final FilterProvider PUBLIC_FILTER =
			new SimpleFilterProvider().addFilter("friendshipDtoFilter", SimpleBeanPropertyFilter.serializeAll());

	public static <T> MappingJacksonValue publicFilter(T dto) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dto);
		mappingJacksonValue.setFilters(PUBLIC_FILTER);
		return mappingJacksonValue;
	}

}
