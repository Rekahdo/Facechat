package com.rekahdo.facechat._populators;

import com.rekahdo.facechat._dtos.AppUserDto;
import com.rekahdo.facechat._repository.AppUserRepository;
import com.rekahdo.facechat._services.AppUserService;
import com.rekahdo.facechat.enums.AuthorityRole;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "prod"})
public class AppUserPopulator {

    private final AppUserService appUserService;
    private final boolean recordInserted;

    @Value("${adminKey}")
    private String adminKey;

    public AppUserPopulator(AppUserService appUserService, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        recordInserted = appUserRepository.existsByUsername("rekahdo");
    }

    @PostConstruct
    private void createUsers(){
        if(!recordInserted){
            AppUserDto rekahdo = new AppUserDto("rekahdo", "ppppPP@1", "okaforrichard76@gmail.com")
                    .setRoles(AuthorityRole.ADMIN.getValue()).setAdminKey(adminKey);

            AppUserDto john = new AppUserDto("john", "ppppPP@1", "johnbillion@gmail.com")
                    .setRoles(AuthorityRole.MODERATOR.getValue()).setAdminKey(adminKey);

            AppUserDto mary = new AppUserDto("mary", "ppppPP@1", "marynwankwo@gmail.com")
                    .setRoles(AuthorityRole.EDITOR.getValue()).setAdminKey(adminKey);

            AppUserDto paul = new AppUserDto("paul", "ppppPP@1", "paul@gmail.com");

            appUserService.createUser(rekahdo);
            appUserService.createUser(john);
            appUserService.createUser(mary);
            appUserService.createUser(paul);
        }
    }

}