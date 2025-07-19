package com.rekahdo.facechat._repository;

import com.rekahdo.facechat._entities.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByUsernameIgnoreCase(String username);
	
	boolean existsByUsername(String username);

	@Transactional @Modifying
	void deleteByUsername(String username);
	
}