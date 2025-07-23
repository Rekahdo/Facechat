package com.rekahdo.facechat.utilities;

import com.rekahdo.facechat._dtos.EntityDto;
import com.rekahdo.facechat._dtos.paginations.PageRequestDto;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@Lazy
public class PageRequestUriBuilder<DTO extends EntityDto<DTO>> {

	public <T> Page<T> getPagedList(PageRequestDto requestDto, List<T> entities){
		// PagedListHolder
		PagedListHolder<T> pagedListHolder = new PagedListHolder<>(entities);
		pagedListHolder.setPage(requestDto.getPage());
		pagedListHolder.setPageSize(requestDto.getSize());

		// Property Comparator
		List<T> pageSlice = pagedListHolder.getPageList();
		boolean ascending = requestDto.isAscend();
		PropertyComparator.sort(pageSlice, new MutableSortDefinition(requestDto.getSortByField(), true, ascending));

		// PageImpl
		return new PageImpl<>(pageSlice, new PageRequestDto().getPageable(requestDto), entities.size());
	}

	public PagedModel<DTO> getPagedModel(PageRequestDto requestDto, Page<DTO> pageDto, Object methodOn){
		PagedModel<DTO> pagedModel = PagedModel.of(pageDto.getContent(),
				new PagedModel.PageMetadata(pageDto.getSize(), pageDto.getNumber(),
						pageDto.getTotalElements(), pageDto.getTotalPages()
				)
		);

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
