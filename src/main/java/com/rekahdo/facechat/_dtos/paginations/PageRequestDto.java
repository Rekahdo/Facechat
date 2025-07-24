package com.rekahdo.facechat._dtos.paginations;

import com.rekahdo.facechat.utilities.StringFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public abstract class PageRequestDto {

	protected Integer page = 0;

	protected Integer size = 10;

	protected boolean ascend = true;

	public String sortByField = "id";

	protected PageRequestDto(Integer size) {
		this.size = size;
	}

	protected PageRequestDto(boolean ascend) {
		this.ascend = ascend;
	}

	protected PageRequestDto(String sortByField) {
		this.sortByField = sortByField;
	}

	protected PageRequestDto(Integer size, boolean ascend) {
		this(size);
		this.ascend = ascend;
	}

	protected PageRequestDto(Integer size, String sortByField) {
		this(size);
		this.sortByField = sortByField;
	}

	protected PageRequestDto(Integer size, boolean ascend, String sortByField) {
		this(size, ascend);
		this.sortByField = sortByField;
	}

	public Pageable getPageable(PageRequestDto dto){
		Integer pageNo = (Objects.nonNull(dto.getPage()) ? dto.getPage() : this.page);
		Integer pageSize = (Objects.nonNull(dto.getSize()) ? dto.getSize() : this.size);
		Sort.Direction sort = (dto.isAscend() ? Sort.Direction.ASC : Sort.Direction.DESC);
		String sortByField = (Objects.nonNull(dto.getSortByField()) ? dto.getSortByField() : this.sortByField);

		return PageRequest.of(pageNo, pageSize, sort, StringFormat.split(sortByField));
	}

}