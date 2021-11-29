package leadmentoring.desafiowebbackend.mappers;

import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.dtos.UsersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UsersMapper {

    public static final UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    public abstract Users toUsers(UsersDTO usersDTO);

}
