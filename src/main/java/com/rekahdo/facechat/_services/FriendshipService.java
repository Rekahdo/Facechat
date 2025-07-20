package com.rekahdo.facechat._services;

import com.rekahdo.facechat._controllers.FriendshipController;
import com.rekahdo.facechat._dtos.AppUserDto;
import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._dtos.PageRequestDto;
import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat._entities.Friendship;
import com.rekahdo.facechat._mappers.AppUserMapper;
import com.rekahdo.facechat._mappers.FriendshipMapper;
import com.rekahdo.facechat._repository.AppUserRepository;
import com.rekahdo.facechat._repository.FriendshipRepository;
import com.rekahdo.facechat.enums.FriendshipStatus;
import com.rekahdo.facechat.exceptions.classes.*;
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
import static com.rekahdo.facechat.enums.FriendshipStatus.*;

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

	@Autowired
	private AppUserMapper userMapper;

	public ResponseEntity<?> addFriendship(Long userId, FriendshipDto dto) {
		if (!appUserRepository.existsById(dto.getFriendId()))
			throw new UserIdNotFoundException(dto.getFriendId());

		if(Objects.equals(userId, dto.getFriendId()))
			throw new SameIdException(userId, dto.getFriendId());

		Long friendId = dto.getFriendId();
		Optional<Friendship> optional = repo.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(userId, friendId, userId, friendId);
		if(optional.isPresent()) throw new FriendshipExistException(friendId);

		dto.setId(null);
		dto.setStatus(FriendshipStatus.PENDING);
		dto.setOnceFriends(false);

		Friendship friendship = mapper.toEntity(dto);
		friendship.setSender(new AppUser(userId));
		friendship.setReceiver(new AppUser(dto.getFriendId()));
		repo.save(friendship);
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<?> editFriendship(Long userId, FriendshipDto dto) {
		Long friendId = dto.getFriendId();
		Optional<Friendship> optional = repo.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(userId, friendId, userId, friendId);
		if(optional.isEmpty()) throw new FriendshipNotFoundException(userId, friendId);

		Friendship friendship = optional.get();
		dto.setOnceFriends(friendship.isOnceFriends());
		if(dto.getStatus() != null){
			boolean isUserIdSender = Objects.equals(userId, friendship.getSender().getId());
			boolean isUserIdReceiver = Objects.equals(userId, friendship.getReceiver().getId());
			FriendshipStatus oldStatus = friendship.getStatus();
			FriendshipStatus newStatus = dto.getStatus();

			if(oldStatus.equals(newStatus))
				dto.setStatus(null);
			else{
				if(oldStatus.equals(PENDING) && newStatus.equals(ACCEPTED) && isUserIdSender)
					throw new OnlyReceiverException("Only the receiver can accept your friend request.");

				else if(oldStatus.equals(PENDING) && newStatus.equals(ACCEPTED) && isUserIdReceiver) {
					dto.setAcceptedAt(Instant.now());
					dto.setOnceFriends(true);
				}

				else if(oldStatus.equals(ACCEPTED) && newStatus.equals(PENDING))
					throw new ModificationException("You are already friends, and can not modify status to 'PENDING'");

				else if(oldStatus.equals(BLOCKED) && newStatus.equals(PENDING) && friendship.isOnceFriends())
					throw new ModificationException(String.format("You were once friends with user id '%d'. Modify status to 'ACCEPTED' to unblock", friendId));

				else if(oldStatus.equals(BLOCKED) && newStatus.equals(ACCEPTED) && !friendship.isOnceFriends())
					throw new ModificationException(String.format("You were never friends with user id '%d'. Modify status to 'PENDING' to unblock", friendId));

				dto.setFriendId(null);
				mapper.updateEntity(dto, friendship);
				repo.save(friendship);
			}
		}

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
		boolean ascending = dto.isAscend();
		PropertyComparator.sort(pageSlice, new MutableSortDefinition(dto.getSortByField(), true, ascending));

		// PageImpl
		Page<Friendship> friendshipPage = new PageImpl<>(pageSlice, new PageRequestDto().getPageable(dto), friendships.size());
		Page<FriendshipDto> friendshipsDtos = friendshipPage.map(friendship -> {
			FriendshipDto dto2 = mapper.toDto(friendship);
			dto2.setFriend(getFriendDto(friendship, userId));
			return dto2;
		});

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

		FriendshipDto dto = mapper.toDto(optional.get());
		dto.setFriend(getFriendDto(optional.get(), userId));
		return ResponseEntity.ok(FriendshipMJV.friendshipFilter(dto));
	}

	public ResponseEntity<?> deleteFriendship(Long userId, Long friendId) {
		repo.deleteByUserIdAndFriendId(userId, friendId);
		return ResponseEntity.ok().build();
	}

	private AppUserDto getFriendDto(Friendship friendship, Long userId){
		AppUser user = Objects.equals(friendship.getSender().getId(), userId)
				? friendship.getReceiver() : friendship.getSender();;
		return userMapper.toDto(user);
	}
}
