package com.rekahdo.facechat._services;

import com.rekahdo.facechat._controllers.FriendshipController;
import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._dtos.PageRequestDto;
import com.rekahdo.facechat._entities.Friendship;
import com.rekahdo.facechat._mappers.FriendshipMapper;
import com.rekahdo.facechat._repository.AppUserRepository;
import com.rekahdo.facechat._repository.FriendshipRepository;
import com.rekahdo.facechat.enums.FriendshipStatus;
import com.rekahdo.facechat.exceptions.classes.EmptyListException;
import com.rekahdo.facechat.exceptions.classes.FriendshipNotFoundException;
import com.rekahdo.facechat.exceptions.classes.SameIdException;
import com.rekahdo.facechat.exceptions.classes.UserIdNotFoundException;
import com.rekahdo.facechat.mappingJacksonValue.FriendshipMJV;
import com.rekahdo.facechat.utilities.PageRequestUriBuilder;
import com.rekahdo.facechat.utilities.StringFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FriendshipService {

	@Autowired
	private FriendshipRepository repo;

	@Autowired
	private FriendshipMapper mapper;

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private PageRequestUriBuilder pageLinkBuilder;

	public ResponseEntity<?> addFriendship(Long senderId, FriendshipDto dto) {
		if (!appUserRepository.existsById(dto.getReceiverId()))
			throw new UserIdNotFoundException(dto.getReceiverId());

		if(Objects.equals(senderId, dto.getReceiverId()))
			throw new SameIdException(senderId, dto.getReceiverId());

		dto.setId(null);
		dto.setStatus(FriendshipStatus.PENDING);
		dto.setCreatedAt(Instant.now());
		dto.setSenderId(senderId);

		repo.save(mapper.toEntity(dto));
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<?> getFriendships(Long userId, PageRequestDto dto, String status) {
		List<FriendshipStatus> statuses = Arrays.stream(StringFormat.split(status))
				.map(FriendshipStatus::findByValue).toList();
		List<Friendship> friendships = repo.findByUserIdAndStatusIn(userId, statuses);
		if (friendships.isEmpty()) throw new EmptyListException();

		// PagedListHolder
		PagedListHolder<Friendship> pagedListHolder = new PagedListHolder<>(friendships);
		pagedListHolder.setPage(dto.getPage());
		pagedListHolder.setPageSize(dto.getSize());

		// Property Comparator
		List<Friendship> pageSlice = pagedListHolder.getPageList();
		boolean ascending = dto.getSort().isAscending();
		PropertyComparator.sort(pageSlice, new MutableSortDefinition(dto.getSortByField(), true, ascending));

		// PageImpl
		Page<Friendship> friendshipPage = new PageImpl<>(pageSlice, new PageRequestDto().getPageable(dto), friendships.size());
		Page<FriendshipDto> friendshipsDtos = friendshipPage.map(mapper::toDto);

		PagedModel<FriendshipDto> pagedModel = PagedModel.of(friendshipsDtos.getContent(),
				new PagedModel.PageMetadata(friendshipsDtos.getSize(), friendshipsDtos.getNumber(),
						friendshipsDtos.getTotalElements(), friendshipsDtos.getTotalPages()
				)
		);

		if(friendshipsDtos.hasNext())
			pagedModel.add(pageLinkBuilder.buildNextLink(methodOn(FriendshipController.class).getFriendships(userId, dto, status), dto));

		if(friendshipsDtos.hasPrevious())
			pagedModel.add(pageLinkBuilder.buildPrevLink(methodOn(FriendshipController.class).getFriendships(userId, dto, status), dto));

		if(friendshipsDtos.getNumber() != 0)
			pagedModel.add(pageLinkBuilder.buildFirstLink(methodOn(FriendshipController.class).getFriendships(userId, dto, status), dto));

		if(friendshipsDtos.getNumber() != friendshipsDtos.getTotalPages()-1)
			pagedModel.add(pageLinkBuilder.buildLastLink(methodOn(FriendshipController.class).getFriendships(userId, dto, status), dto, pagedModel));

		return ResponseEntity.ok(FriendshipMJV.friendshipFilter(pagedModel));
	}

	public ResponseEntity<?> getFriendship(Long userId, Long friendId) {
		Optional<Friendship> optional = repo.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(userId, friendId, userId, friendId);
		if(optional.isEmpty()) throw new FriendshipNotFoundException(userId, friendId);

		return ResponseEntity.ok(FriendshipMJV.friendshipFilter(mapper.toDto(optional.get())));
	}

	public ResponseEntity<?> editFriendship(Long userId, Long friendId, FriendshipDto dto) {

		return null;
	}

	public ResponseEntity<?> deleteFriendship(Long userId, Long friendId) {

		return null;
	}
}
