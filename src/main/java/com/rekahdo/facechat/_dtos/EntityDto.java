package com.rekahdo.facechat._dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Relation(collectionRelation = "items")
public class EntityDto<T extends EntityDto<T>> extends RepresentationModel<T> {

//	@NotNull(message = "Id field must contain a value")
	private Long id;

}