package com.rekahdo.facechat._repository;

import com.rekahdo.facechat._entities.Chat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE (sender.id = :userId OR receiver.id = :userId)")
    List<Chat> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Chat c WHERE (sender.id = :userId AND receiver.id = :friendId) OR (sender.id = :friendId AND receiver.id = :userId)")
    List<Chat> findAllByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Query("SELECT c FROM Chat c WHERE (sender.id = :userId OR receiver.id = :userId) AND c.id = :chatId")
    Optional<Chat> findByUserIdAndChatId(@Param("userId") Long userId, @Param("chatId") Long chatId);

    @Transactional @Modifying
    @Query("DELETE from Chat WHERE (sender.id = :userId OR receiver.id = :userId) AND id = :chatId ")
    void deleteByUserIdAndId(@Param("userId") Long userId, @Param("chatId") Long chatId);
}