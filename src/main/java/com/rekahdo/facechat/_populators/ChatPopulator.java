package com.rekahdo.facechat._populators;

import com.rekahdo.facechat._dtos.ChatDto;
import com.rekahdo.facechat._repository.AppUserRepository;
import com.rekahdo.facechat._services.ChatService;
import com.rekahdo.facechat.enums.ChatStatus;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@DependsOn({"appUserPopulator", "friendshipPopulator"})
@Profile({"dev", "prod"})
public class ChatPopulator {

    private final long ONE_MINUTE = 60;
    private final long ONE_HOUR = ONE_MINUTE * 60;
    private final long ONE_DAY = ONE_HOUR * 24;
    private Instant START_TIME = Instant.now().minusSeconds(ONE_DAY * 5);
    private Instant CURRENT_TIME = Instant.now();

    private final ChatService service;
    private final boolean recordInserted;

    public ChatPopulator(ChatService service, AppUserRepository appUserRepository) {
        this.service = service;
        recordInserted = appUserRepository.existsByUsername("rekahdo");
    }

    // REKAHDO AND JOHN

    private Instant REK_JOHN = START_TIME;

    private Instant rekJohnAddMinute(){
        REK_JOHN = REK_JOHN.plusSeconds(ONE_MINUTE);
        return REK_JOHN;
    }

    private Instant rekJohnAddMinute(@Nullable Integer multiplyBy){
        REK_JOHN = REK_JOHN.plusSeconds(ONE_MINUTE * multiplyBy);
        return REK_JOHN;
    }

    private Instant rekJohnAddHour(){
        REK_JOHN = REK_JOHN.plusSeconds(ONE_HOUR);
        return REK_JOHN;
    }

    private Instant rekJohnAddHour(int multiplyBy){
        REK_JOHN = REK_JOHN.plusSeconds(ONE_HOUR * multiplyBy);
        return REK_JOHN;
    }

    private Instant rekJohnAddDay(){
        REK_JOHN = REK_JOHN.plusSeconds(ONE_DAY);
        return REK_JOHN;
    }

    private Instant rekJohnAddDay(int multiplyBy){
        REK_JOHN = REK_JOHN.plusSeconds(ONE_DAY * multiplyBy);
        return REK_JOHN;
    }

    @PostConstruct
    private void sentChat_Rekahdo_John(){
        if(recordInserted){
            service.sendChat(2L, new ChatDto("Hello Rekahdo - FIRST", ChatStatus.SEEN, rekJohnAddMinute(), rekJohnAddMinute(), rekJohnAddMinute(), 1L, 1L)); // John
            service.sendChat(2L, new ChatDto("How are you doing", ChatStatus.SEEN, rekJohnAddMinute(), rekJohnAddMinute(), rekJohnAddMinute(), 1L, 1L)); // John
            service.sendChat(1L, new ChatDto("Hello john", ChatStatus.SEEN, rekJohnAddMinute(), rekJohnAddMinute(), rekJohnAddMinute(), 2L, 1L)); // Rekahdo
            service.sendChat(1L, new ChatDto("I am fine", ChatStatus.SEEN, rekJohnAddMinute(), rekJohnAddMinute(), rekJohnAddMinute(), 2L, 1L)); // Rekahdo
            service.sendChat(1L, new ChatDto("How are you doing also", ChatStatus.SEEN, rekJohnAddMinute(), rekJohnAddMinute(), rekJohnAddMinute(), 2L, 1L)); // Rekahdo
            service.sendChat(2L, new ChatDto("I am doing good, i thank God", ChatStatus.SEEN, rekJohnAddMinute(), rekJohnAddMinute(), rekJohnAddMinute(), 1L, 1L)); // John
            service.sendChat(1L, new ChatDto("Alright, that's good", ChatStatus.SEEN, rekJohnAddMinute(), rekJohnAddMinute(), rekJohnAddMinute(), 2L, 1L)); // Rekahdo
            service.sendChat(2L, new ChatDto("Talk later rekahdo, bye bro", ChatStatus.SEEN, rekJohnAddMinute(), rekJohnAddMinute(), rekJohnAddMinute(), 1L, 1L)); // John
            service.sendChat(1L, new ChatDto("Alright, bye bro john", ChatStatus.DELIVERED, rekJohnAddMinute(), rekJohnAddMinute(), null, 2L, 1L)); // Rekahdo
            service.sendChat(1L, new ChatDto("Hello John!!!", ChatStatus.DELIVERED, rekJohnAddDay(4), rekJohnAddHour(1), null, 2L, 1L)); // Rekahdo
            service.sendChat(1L, new ChatDto("Are you there?", ChatStatus.SENT, rekJohnAddHour(6), null, null, 2L, 1L)); // Rekahdo
            service.sendChat(1L, new ChatDto("I am done", ChatStatus.SENT, rekJohnAddMinute(), null, null, 2L, 1L)); // Rekahdo
            service.sendChat(1L, new ChatDto("Hello my bro, you haven't been replying - LAST", ChatStatus.SENT, CURRENT_TIME, null, null, 2L, 1L)); // Rekahdo
        }
    }


    // REKAHDO AND MARY

    private Instant REK_MARY = START_TIME;

    private Instant rekMaryAddMinute(){
        REK_MARY = REK_MARY.plusSeconds(ONE_MINUTE);
        return REK_MARY;
    }

    private Instant rekMaryAddMinute(Integer multiplyBy){
        REK_MARY = REK_MARY.plusSeconds(ONE_MINUTE * multiplyBy);
        return REK_MARY;
    }

    private Instant rekMaryAddHour(){
        REK_MARY = REK_MARY.plusSeconds(ONE_HOUR);
        return REK_MARY;
    }

    private Instant rekMaryAddHour(int multiplyBy){
        REK_MARY = REK_MARY.plusSeconds(ONE_HOUR * multiplyBy);
        return REK_MARY;
    }

    private Instant rekMaryAddDay(){
        REK_MARY = REK_MARY.plusSeconds(ONE_DAY);
        return REK_MARY;
    }

    private Instant rekMaryAddDay(int multiplyBy){
        REK_MARY = REK_MARY.plusSeconds(ONE_DAY * multiplyBy);
        return REK_MARY;
    }

    @PostConstruct
    private void sentChat_Rekahdo_Mary(){
        if(recordInserted){
            service.sendChat(1L, new ChatDto("Hello mary - FIRST", ChatStatus.SEEN, rekMaryAddMinute(), rekMaryAddMinute(), rekMaryAddMinute(), 3L)); // Rekahdo
            service.sendChat(3L, new ChatDto("Hello Rekahdo", ChatStatus.SEEN, rekMaryAddMinute(), rekMaryAddMinute(), rekMaryAddMinute(), 1L)); // Mary
            service.sendChat(1L, new ChatDto("Alright", ChatStatus.SEEN, rekMaryAddMinute(), rekMaryAddMinute(), rekMaryAddMinute(), 3L)); // Rekahdo
            service.sendChat(3L, new ChatDto("Yea, bye now rekahdo", ChatStatus.SEEN, rekMaryAddMinute(), rekMaryAddMinute(), rekMaryAddMinute(), 1L)); // Mary
            service.sendChat(1L, new ChatDto("Alright mary, bye", ChatStatus.DELIVERED, rekMaryAddMinute(), rekMaryAddMinute(), null, 3L)); // Rekahdo
            service.sendChat(1L, new ChatDto("Hello mary", ChatStatus.DELIVERED, rekMaryAddDay(3), rekMaryAddHour(5), null, 3L)); // Rekahdo
            service.sendChat(1L, new ChatDto("How are you doing today?", ChatStatus.SENT, rekMaryAddMinute(10), null, null, 3L)); // Rekahdo
            service.sendChat(1L, new ChatDto("Hi Mary Nwankwo - LAST", ChatStatus.SENT, CURRENT_TIME, null, null, 3L)); // Rekahdo
        }
    }


    // MARY AND JOHN

    private Instant MARY_JOHN = START_TIME;

    private Instant maryJohnAddMinute(){
        MARY_JOHN = MARY_JOHN.plusSeconds(ONE_MINUTE);
        return MARY_JOHN;
    }

    private Instant maryJohnAddMinute(Integer multiplyBy){
        MARY_JOHN = MARY_JOHN.plusSeconds(ONE_MINUTE * multiplyBy);
        return MARY_JOHN;
    }

    private Instant maryJohnAddHour(){
        MARY_JOHN = MARY_JOHN.plusSeconds(ONE_HOUR);
        return MARY_JOHN;
    }

    private Instant maryJohnAddHour(int multiplyBy){
        MARY_JOHN = MARY_JOHN.plusSeconds(ONE_HOUR * multiplyBy);
        return MARY_JOHN;
    }

    private Instant maryJohnAddDay(){
        MARY_JOHN = MARY_JOHN.plusSeconds(ONE_DAY);
        return MARY_JOHN;
    }

    private Instant maryJohnAddDay(int multiplyBy){
        MARY_JOHN = MARY_JOHN.plusSeconds(ONE_DAY * multiplyBy);
        return MARY_JOHN;
    }

    @PostConstruct
    private void sentChat_Mary_John(){
        if(recordInserted){
            service.sendChat(2L, new ChatDto("Hello mary - FIRST", ChatStatus.SEEN, maryJohnAddDay(), maryJohnAddHour(), maryJohnAddMinute(), 3L)); // John
            service.sendChat(3L, new ChatDto("Hello John", ChatStatus.SEEN, maryJohnAddMinute(), maryJohnAddMinute(), maryJohnAddMinute(), 2L)); // Mary
            service.sendChat(3L, new ChatDto("I'm kinda busy at the moment", ChatStatus.SEEN, maryJohnAddHour(), maryJohnAddMinute(), maryJohnAddMinute(), 2L)); // Mary
            service.sendChat(3L, new ChatDto("We'll talk later", ChatStatus.SEEN, maryJohnAddMinute(), maryJohnAddMinute(), maryJohnAddMinute(), 2L)); // Mary
            service.sendChat(2L, new ChatDto("Alright - FAKE LAST", ChatStatus.DELIVERED, maryJohnAddMinute(), maryJohnAddMinute(30), null, 3L)); // John
            service.sendChat(2L, new ChatDto("Hi, are you done Mary - LAST", ChatStatus.SENT, CURRENT_TIME, null, null, 3L)); // John
        }
    }

}