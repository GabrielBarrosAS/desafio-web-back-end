package leadmentoring.desafiowebbackend.util.UsersCreator;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Users;

import java.time.LocalDateTime;

public class UsersCreator {
    public static Language LANGUAGE_USERS;

    public static Users createUsersToBeSaved(){
        return Users.builder()
                .name("User name")
                .telephone("999999999")
                .roles("ROLE_USER")
                .profile("URL PROFILE")
                .password("password")
                .cpf("80380083086")
                .email("email@email.com")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .language(LANGUAGE_USERS)
                .build();
    }

    public static Users createUsersSaved(){
        return Users.builder()
                .id(1)
                .name("User name")
                .telephone("999999999")
                .roles("ROLE_USER")
                .profile("URL PROFILE")
                .password("password")
                .cpf("80380083086")
                .email("email@email.com")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .language(LANGUAGE_USERS)
                .build();
    }

}
