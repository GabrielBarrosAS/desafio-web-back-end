package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPutDTO;
import leadmentoring.desafiowebbackend.dtos.usersDTOS.UsersPostDTO;
import leadmentoring.desafiowebbackend.exception.BadRequestException;
import leadmentoring.desafiowebbackend.exception.ForbiddenException;
import leadmentoring.desafiowebbackend.mappers.UsersMapper;
import leadmentoring.desafiowebbackend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final LanguageService languageService;
    private final PasswordEncoder encoder;

    public List<Users> listAllNoPageable(){

        return usersRepository.findAll();
    }

    public Users findById(Long id){
        return usersRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    public Users save(UsersPostDTO usersPostDTO){

        Users newUser = UsersMapper.INSTANCE.toUsers(usersPostDTO);

        Language language = languageService.findById(newUser.getLanguage().getId());

        List<Users> emailNotFound = usersRepository.findByEmail(newUser.getEmail());

        if (!emailNotFound.isEmpty()){
            throw new BadRequestException("Email registered in the system");
        }

        List<Users> cpfNotFound = usersRepository.findByCpf(newUser.getCpf());

        if (!cpfNotFound.isEmpty()){
            throw new BadRequestException("Cpf registered in the system");
        }

        BeanUtils.copyProperties(language, newUser.getLanguage());

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        newUser.setActive(true);

        return usersRepository.save(newUser);
    }

    public Users update(UsersPutDTO usersPutDTO, UserDetails userDetails){

        Users userPut = UsersMapper.INSTANCE.toUsers(usersPutDTO);

        Users databaseUser = findById(userPut.getId());

        Boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        log.info(isAdmin);
        if (!isAdmin && !userDetails.getUsername().equals(databaseUser.getEmail())) {
            throw new ForbiddenException("Acesso negado! Não é possível alterar os dados de outro cliente");
        }

        List<Users> emailNotFound = usersRepository.findByEmail(userPut.getEmail());

        if (emailNotFound.size() == 1 && !databaseUser.getEmail().equals(userPut.getEmail())){
            throw new BadRequestException("Email registered in the system");
        }

        List<Users> cpfNotFound = usersRepository.findByCpf(userPut.getCpf());

        if (cpfNotFound.size() == 1 && !databaseUser.getCpf().equals(userPut.getCpf())){
            throw new BadRequestException("Cpf registered in the system");
        }

        userPut.setPassword(encoder.encode(userPut.getPassword()));

        Language language = languageService.findById(userPut.getLanguage().getId());

        BeanUtils.copyProperties(language, userPut.getLanguage());

        BeanUtils.copyProperties(userPut,databaseUser, "createdAt");

        return usersRepository.save(databaseUser);
    }

    public Users delete(long id){
        Users user = findById(id);

        user.setActive(false);

        usersRepository.save(user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        return Optional.ofNullable(usersRepository.findByEmail(email).get(0))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
