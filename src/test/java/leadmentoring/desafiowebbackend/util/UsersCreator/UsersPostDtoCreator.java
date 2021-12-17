package leadmentoring.desafiowebbackend.util.UsersCreator;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPostDTO;

public class UsersPostDtoCreator {

    public static Language LANGUAGE_POST_USERS;

    public static UsersPostDTO createUsersPostDtoToBeSaved(){
        return UsersPostDTO.builder()
                .name("User name")
                .telephone("999999999")
                .roles("ROLE_USER")
                .profile("URL PROFILE")
                .password("password")
                .cpf("80380083086")
                .email("email@email.com")
                .languageID(LANGUAGE_POST_USERS.getId())
                .build();
    }

}
