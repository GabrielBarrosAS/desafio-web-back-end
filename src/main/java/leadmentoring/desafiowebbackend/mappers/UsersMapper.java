package leadmentoring.desafiowebbackend.mappers;

import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPutDTO;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UsersMapper {

    public static final UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    public abstract Users toUsers(UsersPostDTO usersPostDTO);

    public abstract Users toUsers(UsersPutDTO usersPutDTO);

}
