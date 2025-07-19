package com.rekahdo.facechat._populators;

import com.rekahdo.facechat._dtos.AppUserDto;
import com.rekahdo.facechat._dtos.FriendshipDto;
import com.rekahdo.facechat._entities.Friendship;
import com.rekahdo.facechat._repository.AppUserRepository;
import com.rekahdo.facechat._services.AppUserService;
import com.rekahdo.facechat._services.FriendshipService;
import com.rekahdo.facechat.enums.AuthorityRole;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@DependsOn("appUserPopulator")
@Profile({"dev", "prod"})
public class FriendshipPopulator {

    private final FriendshipService service;
    private final AppUserRepository appUserRepository;
    private final boolean recordInserted;

    public FriendshipPopulator(FriendshipService service, AppUserRepository appUserRepository) {
        this.service = service;
        this.appUserRepository = appUserRepository;
        recordInserted = appUserRepository.existsByUsername("rekahdo");
    }

    @PostConstruct
    private void addFriendships(){
        if(recordInserted){
            FriendshipDto rekJohn = new FriendshipDto(2L);
            service.addFriendship(1L, rekJohn);

//            FriendshipDto rekPaul = new FriendshipDto(4L);
//            service.addFriendship(1L, rekPaul);
//
//            FriendshipDto maryRek = new FriendshipDto(1L);
//            service.addFriendship(3L, maryRek);
//
//            FriendshipDto maryJohn = new FriendshipDto(2L);
//            service.addFriendship(3L, maryJohn);
        }
    }

}