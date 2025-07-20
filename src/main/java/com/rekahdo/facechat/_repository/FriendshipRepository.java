package com.rekahdo.facechat._repository;

import java.util.List;
import java.util.Optional;

import com.rekahdo.facechat._entities.Friendship;
import com.rekahdo.facechat.enums.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
	
	@Query("SELECT f FROM Friendship f WHERE (sender.id = :userId OR receiver.id = :userId) AND  f.status IN :statuses")
	List<Friendship> findByUserIdAndStatusIn(@Param("userId") Long userId, @Param("statuses") List<FriendshipStatus> statuses);

	Optional<Friendship> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(Long senderId, Long receiverId, Long receiverId2, Long senderId2);

}
