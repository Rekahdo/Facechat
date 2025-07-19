package com.rekahdo.facechat.utilities;

import com.rekahdo.facechat._dtos.PageRequestDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@Lazy
public class PageRequestUriBuilder {

    private Link buildLink(Object methodOn, PageRequestDto dto, Integer page, String relation) {
        UriComponentsBuilder builder = linkTo(methodOn).toUriComponentsBuilder()
                .queryParam("page", page)
                .queryParam("size", dto.getSize())
                .queryParam("sort", dto.getSort())
                .queryParam("sortByField", dto.getSortByField());
		return Link.of(builder.build().toUriString(), relation);
	}

	public Link buildNextLink(Object methodOn, PageRequestDto dto) {
		return buildLink(methodOn, dto, dto.getPage()+1, "next");
	}

	public Link buildPrevLink(Object methodOn, PageRequestDto dto) {
		return buildLink(methodOn, dto, dto.getPage()-1, "prev");
	}

	public Link buildFirstLink(Object methodOn, PageRequestDto dto) {
		return buildLink(methodOn, dto, 0, "first");
	}

	public Link buildLastLink(Object invocationValue, PageRequestDto dto, PagedModel<?> pagedModel) {
        assert pagedModel.getMetadata() != null;
        return buildLink(invocationValue, dto, (int)(pagedModel.getMetadata().getTotalPages()-1), "last");
	}

}
