package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.dtos.UserPutDTO;
import leadmentoring.desafiowebbackend.dtos.UsersPostDTO;
import leadmentoring.desafiowebbackend.exception.BadRequestException;
import leadmentoring.desafiowebbackend.mappers.UsersMapper;
import leadmentoring.desafiowebbackend.repository.LanguageRepository;
import leadmentoring.desafiowebbackend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UsersRepository usersRepository;
    private final LanguageRepository languageRepository;

    public List<Users> listAllNoPageable(){
        return usersRepository.findAll();
    }

    public Users findById(Long id){
        return usersRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    public Users save(UsersPostDTO usersPostDTO){

        Users newUser = UsersMapper.INSTANCE.toUsers(usersPostDTO);

        Optional<Language> language = languageRepository.findById(newUser.getLanguage().getId());

        if (language.isEmpty()) {
            throw new BadRequestException("Language not found");
        }

        List<Users> emailNotFound = usersRepository.findByEmail(newUser.getEmail());

        if (!emailNotFound.isEmpty()){
            throw new BadRequestException("Email registered in the system");
        }

        List<Users> cpfNotFound = usersRepository.findByCpf(newUser.getCpf());

        if (!cpfNotFound.isEmpty()){
            throw new BadRequestException("Cpf registered in the system");
        }

        newUser.setActive(true);

        return usersRepository.save(newUser);
    }

    public Users update(UserPutDTO userPutDTO){

        Users userPut = UsersMapper.INSTANCE.toUsers(userPutDTO);

        Users databaseUser = findById(userPut.getId());

        List<Users> emailNotFound = usersRepository.findByEmail(userPut.getEmail());

        if (emailNotFound.size() == 1 && !databaseUser.getEmail().equals(userPut.getEmail())){
            throw new BadRequestException("Email registered in the system");
        }

        List<Users> cpfNotFound = usersRepository.findByCpf(userPut.getCpf());

        if (cpfNotFound.size() == 1 && !databaseUser.getCpf().equals(userPut.getCpf())){
            throw new BadRequestException("Cpf registered in the system");
        }

        BeanUtils.copyProperties(userPut,databaseUser, "createdAt");

        return usersRepository.save(databaseUser);
    }

    public Users delete(long id){
        Users user = findById(id);

        user.setActive(false);

        usersRepository.save(user);

        return user;
    }

}
