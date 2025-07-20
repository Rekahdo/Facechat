package com.rekahdo.facechat.utilities;

import com.rekahdo.facechat._dtos.EntityDto;
import com.rekahdo.facechat._dtos.PageRequestDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@Lazy
public class PageRequestUriBuilder<T extends EntityDto<T>> {

	private PageRequestDto requestDto;
	private Page<T> pageDto;
	private Object methodOn;
	private PagedModel<T> pagedModel;

	public PageRequestUriBuilder<T> initialize(PageRequestDto requestDto, Page<T> pageDto, Object methodOn) {
		this.requestDto = requestDto;
		this.pageDto = pageDto;
		this.methodOn = methodOn;
		this.pagedModel = PagedModel.of(pageDto.getContent(),
				new PagedModel.PageMetadata(pageDto.getSize(), pageDto.getNumber(),
						pageDto.getTotalElements(), pageDto.getTotalPages()
				)
		);

		return this;
	}

	public PagedModel<T> build(){
		if(pagedModel != null){

			if(pageDto.hasNext()){
				Link link = buildLink(methodOn, requestDto, requestDto.getPage()+1, "next");
				pagedModel.add(link);
			}

			if(pageDto.hasPrevious()){
				Link link = buildLink(methodOn, requestDto, requestDto.getPage()-1, "prev");
				pagedModel.add(link);
			}

			if(pageDto.getNumber() != 0){
				Link link = buildLink(methodOn, requestDto, 0, "first");
				pagedModel.add(link);
			}

			if(pageDto.getNumber() != pageDto.getTotalPages()-1){
				assert pagedModel.getMetadata() != null;
				Link link = buildLink(methodOn, requestDto, (int)(pagedModel.getMetadata().getTotalPages()-1), "last");
				pagedModel.add(link);
			}
		}

		return pagedModel;
	}

	private Link buildLink(Object methodOn, PageRequestDto dto, Integer page, String relation) {
        UriComponentsBuilder builder = linkTo(methodOn).toUriComponentsBuilder()
                .queryParam("page", page)
                .queryParam("size", dto.getSize())
                .queryParam("ascend", dto.isAscend())
                .queryParam("sortByField", dto.getSortByField());
		return Link.of(builder.build().toUriString(), relation);
	}

}
