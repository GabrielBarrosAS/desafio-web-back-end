package leadmentoring.desafiowebbackend.util.UsersCreator;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPutDTO;

public class UsersPutDtoCreator {

    public static Language LANGUAGE_PUT_USERS;

    public static UsersPutDTO createUsersPutDtoToBeUpdate(){
        return UsersPutDTO.builder()
                .id(1L)
                .name("User name update")
                .telephone("update")
                .roles("ROLE_USER")
                .profile("URL PROFILE update")
                .password("password update")
                .cpf("80380083086")
                .email("email@email.com update")
                .languageID(LANGUAGE_PUT_USERS.getId())
                .build();
    }
}
