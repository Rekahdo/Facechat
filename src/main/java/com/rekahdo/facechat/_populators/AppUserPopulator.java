package com.rekahdo.facechat._populators;

import com.rekahdo.facechat._dtos.entities.AppUserDto;
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

    private final AppUserService service;
    private final boolean recordInserted;

    @Value("${adminKey}")
    private String adminKey;

    public AppUserPopulator(AppUserService service, AppUserRepository appUserRepository) {
        this.service = service;
        recordInserted = appUserRepository.existsByUsername("rekahdo");
    }

    @PostConstruct
    private void createUsers(){
        if(!recordInserted){
            AppUserDto rekahdo = new AppUserDto("rekahdo", "ppppPP@1", "okaforrichard76@gmail.com")
                    .setRoles(AuthorityRole.ADMIN.getValue()).setAdminKey(adminKey);
            service.createUser(rekahdo);

            AppUserDto john = new AppUserDto("john", "ppppPP@1", "johnbillion@gmail.com")
                    .setRoles(AuthorityRole.MODERATOR.getValue()).setAdminKey(adminKey);
            service.createUser(john);

            AppUserDto mary = new AppUserDto("mary", "ppppPP@1", "marynwankwo@gmail.com")
                    .setRoles(AuthorityRole.EDITOR.getValue()).setAdminKey(adminKey);
            service.createUser(mary);

            service.createUser(new AppUserDto("paul", "ppppPP@1", "paul@gmail.com"));
            service.createUser(new AppUserDto("joe", "ppppPP@1", "joe@gmail.com"));
            service.createUser(new AppUserDto("jack", "ppppPP@1", "jack@gmail.com"));
            service.createUser(new AppUserDto("james", "ppppPP@1", "james@gmail.com"));
            service.createUser(new AppUserDto("josh", "ppppPP@1", "josh@gmail.com"));
        }
    }

}