package com.rekahdo.facechat._populators;

import com.rekahdo.facechat._dtos.entities.FriendshipDto;
import com.rekahdo.facechat._repository.AppUserRepository;
import com.rekahdo.facechat._services.FriendshipService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@DependsOn("appUserPopulator")
@Profile({"dev", "prod"})
public class FriendshipPopulator {

    private final FriendshipService service;
    private final boolean recordInserted;

    public FriendshipPopulator(FriendshipService service, AppUserRepository appUserRepository) {
        this.service = service;
        recordInserted = appUserRepository.existsByUsername("rekahdo");
    }

    @PostConstruct
    private void addFriendships(){
        if(recordInserted){
            // rekahdo and john
            service.addFriendship(1L, new FriendshipDto(2L));

            // rekahdo and paul
            service.addFriendship(1L, new FriendshipDto(4L));

            // rekahdo and jack
            service.addFriendship(6L, new FriendshipDto(1L));

            // rekahdo and josh
            service.addFriendship(8L, new FriendshipDto(1L));

            // mary and john
            service.addFriendship(3L, new FriendshipDto(2L));
        }
    }

}