package leadmentoring.desafiowebbackend.repository;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.util.CategoryCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator;
import leadmentoring.desafiowebbackend.util.UsersCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for the users repository")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Log4j2
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @BeforeAll
    void setUp(){
        this.languageRepository.save(LanguageCreator.createLanguageSaved());
    }

    @Test
    @DisplayName("Persists the new user when it occurs successfully")
    void save_PersistUsers_WhenSuccessful(){

        Users userToBeSaved = UsersCreator.createUsersToBeSaved();
        Users savedUsers = this.usersRepository.save(userToBeSaved);

        Assertions.assertThat(savedUsers).isNotNull();
        Assertions.assertThat(savedUsers.getId()).isNotNull();
        Assertions.assertThat(savedUsers).usingRecursiveComparison().isEqualTo(userToBeSaved);
    }

    @Test
    @DisplayName("Throws ConstraintViolationException when passing an invalid field")
    void save_ThrowsConstraintViolationException_WhenPassingAnInvalidField(){

        Users userToBeSavedNameNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedNameNull.setName(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedNameNull))
                .isInstanceOf(ConstraintViolationException.class);

        Users userToBeSavedLanguageNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedLanguageNull.setLanguage(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedLanguageNull))
                .isInstanceOf(ConstraintViolationException.class);

        Users userToBeSavedActiveNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedActiveNull.setActive(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedActiveNull))
                .isInstanceOf(ConstraintViolationException.class);

        Users userToBeSavedTelephoneNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedTelephoneNull.setTelephone(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedTelephoneNull))
                .isInstanceOf(ConstraintViolationException.class);

        Users userToBeSavedEmailNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedEmailNull.setEmail(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedEmailNull))
                .isInstanceOf(ConstraintViolationException.class);

        Users userToBeSavedProfileNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedProfileNull.setProfile(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedProfileNull))
                .isInstanceOf(ConstraintViolationException.class);

        Users userToBeSavedRolesNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedRolesNull.setRoles(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedRolesNull))
                .isInstanceOf(ConstraintViolationException.class);

        Users userToBeSavedPasswordNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedPasswordNull.setPassword(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedPasswordNull))
                .isInstanceOf(ConstraintViolationException.class);

        Users userToBeSavedCPFNull = UsersCreator.createUsersToBeSaved();
        userToBeSavedCPFNull.setCpf(null);
        Assertions.assertThatThrownBy(() -> this.usersRepository.save(userToBeSavedCPFNull))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Return a user with id corresponding to the fetched")
    void findById_ReturnUsers_WhenCorrespondingId(){

        Users savedUsers = this.usersRepository.save(UsersCreator.createUsersToBeSaved());

        Optional<Users> findByIdUsers = this.usersRepository.findById(savedUsers.getId());

        Assertions.assertThat(findByIdUsers)
                .isNotEmpty()
                .isNotNull();
        Assertions.assertThat(findByIdUsers.get()).usingRecursiveComparison().isEqualTo(savedUsers);
    }

    @Test
    @DisplayName("Returns an empty Optional when ID does not match")
    void findById_ReturnEmptyOptional_WhenDoesNotCorrespondingId(){
        Optional<Users> findByIdUsers = this.usersRepository.findById(0L);

        Assertions.assertThat(findByIdUsers )
                .isEmpty()
                .isNotNull();
    }

    @Test
    @DisplayName("Returns a list of all as users stored in the database")
    void findAll_ReturnListUsers_WhenThereAreUsers(){

        List<Language> language = languageRepository.findAll();

        Users categoryToBeSavedOne = UsersCreator.createUsersToBeSaved();
        Users categoryToBeSavedTwo = UsersCreator.createUsersToBeSaved();

        Users savedUsersOne = this.usersRepository.save(categoryToBeSavedOne);
        Users savedUsersTwo = this.usersRepository.save(categoryToBeSavedTwo);

        List<Users> listUsers = this.usersRepository.findAll();

        Assertions.assertThat(listUsers )
                .isNotNull()
                .isNotEmpty()
                .contains(savedUsersOne)
                .contains(savedUsersTwo)
                .hasSize(2);
    }

    @Test
    @DisplayName("Returns an empty list when there are no users stored")
    void findAll_ReturnEmptyListUsers_WhenThereAreNoUsers(){
        List<Users > listUsers = this.usersRepository.findAll();

        Assertions.assertThat(listUsers )
                .isNotNull()
                .isEmpty();
    }
}