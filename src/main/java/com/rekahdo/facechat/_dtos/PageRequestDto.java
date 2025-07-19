package com.rekahdo.facechat._dtos;

import com.rekahdo.facechat.utilities.StringFormat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PageRequestDto {

	private Integer page = 0;

	private Integer size = 2;

	private Sort.Direction sort = Sort.Direction.ASC;

	private String sortByField = "id";

	public Pageable getPageable(PageRequestDto dto){
		Integer pageNo = (Objects.nonNull(dto.getPage()) ? dto.getPage() : this.page);
		Integer pageSize = (Objects.nonNull(dto.getSize()) ? dto.getSize() : this.size);
		Sort.Direction sort = (Objects.nonNull(dto.getSort()) ? dto.getSort() : this.sort);
		String sortByField = (Objects.nonNull(dto.getSortByField()) ? dto.getSortByField() : this.sortByField);

        return PageRequest.of(pageNo, pageSize, sort, StringFormat.split(sortByField));
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Sort.Direction getSort() {
		return sort;
	}

	public void setSort(Sort.Direction sort) {
		this.sort = sort;
	}

	public String getSortByField() {
		return sortByField;
	}

	public void setSortByField(String sortByField) {
		this.sortByField = sortByField;
	}
}