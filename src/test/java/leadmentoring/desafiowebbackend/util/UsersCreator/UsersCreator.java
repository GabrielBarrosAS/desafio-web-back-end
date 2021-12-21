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
                .language(LANGUAGE_USERS)
                .build();
    }

    public static Users createUsersSavedAdmin(){
        return Users.builder()
                .id(1)
                .name("User name")
                .telephone("999999999")
                .roles("ROLE_USER,ROLE_ADMIN")
                .profile("URL PROFILE")
                .password("password")
                .cpf("80380083086")
                .email("email@email.com")
                .active(true)
                .language(LANGUAGE_USERS)
                .build();
    }

    public static Users createUsersUpdated(){
        return Users.builder()
                .id(1L)
                .name("User name update")
                .telephone("update")
                .roles("ROLE_USER")
                .profile("update")
                .password("password")
                .cpf("80380083086")
                .email("email@email.comupdate")
                .active(true)
                .language(LANGUAGE_USERS)
                .build();
    }

    public static Users createUsersDeleted(){
        return Users.builder()
                .id(1)
                .name("User name")
                .telephone("999999999")
                .roles("ROLE_USER")
                .profile("URL PROFILE")
                .password("password")
                .cpf("80380083086")
                .email("email@email.com")
                .active(false)
                .language(LANGUAGE_USERS)
                .build();
    }

}
