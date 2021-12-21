package leadmentoring.desafiowebbackend.util.UsersCreator;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPutDTO;

public class UsersPutDtoCreator {

    public static Language LANGUAGE_PUT_USERS;

    public static UsersPutDTO createUsersPutDtoToBeUpdate(){
        return UsersPutDTO.builder()
                .id(1)
                .name("User name update")
                .telephone("888888888")
                .roles("ROLE_USER")
                .profile("URL PROFILE update")
                .password("password update")
                .cpf("80380083086")
                .email("email@email.comupdate")
                .active(true)
                .languageID(LANGUAGE_PUT_USERS.getId())
                .build();
    }
}
