package com.rekahdo.facechat._repository;

import com.rekahdo.facechat._entities.Authority;
import com.rekahdo.facechat.enums.AuthorityRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Authority WHERE appUser.id = :userId")
    void deleteByAppUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Authority WHERE appUser.id = :userId AND role IN :roles")
    void deleteAllByAppUserIdAndRoleIn(@Param("userId") Long userId, @Param("roles") List<AuthorityRole> roles);

}
