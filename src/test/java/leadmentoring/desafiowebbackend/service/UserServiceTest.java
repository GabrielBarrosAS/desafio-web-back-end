package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.exception.badRequest.BadRequestException;
import leadmentoring.desafiowebbackend.exception.forbidden.ForbiddenException;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundException;
import leadmentoring.desafiowebbackend.repository.UsersRepository;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
import leadmentoring.desafiowebbackend.util.UsersCreator.UsersCreator;
import leadmentoring.desafiowebbackend.util.UsersCreator.UsersPostDtoCreator;
import leadmentoring.desafiowebbackend.util.UsersCreator.UsersPutDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private LanguageService languageServiceMock;

    @Mock
    private PasswordEncoder encoderMock;

    @BeforeAll
    void setUpAll(){
        UsersCreator.LANGUAGE_USERS = LanguageCreator.createLanguageSaved();
        UsersPostDtoCreator.LANGUAGE_POST_USERS = LanguageCreator.createLanguageSaved();
        UsersPutDtoCreator.LANGUAGE_PUT_USERS = LanguageCreator.createLanguageSaved();
    }

    @AfterAll
    void toClear(){
        UsersCreator.LANGUAGE_USERS = null;
        UsersPostDtoCreator.LANGUAGE_POST_USERS = null;
        UsersPutDtoCreator.LANGUAGE_PUT_USERS = null;
    }

    @BeforeEach
    void setUp(){
        BDDMockito.when(usersRepositoryMock.findAll())
                .thenReturn(List.of(UsersCreator.createUsersSaved()));

        BDDMockito.when(usersRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UsersCreator.createUsersSaved()));

        BDDMockito.when(usersRepositoryMock.save(ArgumentMatchers.any(Users.class)))
                .thenReturn(UsersCreator.createUsersSaved());

        BDDMockito.when(usersRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UsersCreator.createUsersSaved()));

        BDDMockito.when(usersRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UsersCreator.createUsersSaved()));

        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(LanguageCreator.createLanguageSaved());

        BDDMockito.when(encoderMock.encode(ArgumentMatchers.any()))
                .thenReturn("password");
    }

    @Test
    @DisplayName("ListAllNoPegeable returns list of userss when sucessful")
    void listAllNoPegeable_ReturnsListOfUserss_WhenSucessful(){

        Users usersExpected = UsersCreator.createUsersSaved();

        List<Users> users = userService.listAllNoPageable();

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0)).usingRecursiveComparison().isEqualTo(usersExpected);

    }

    @Test
    @DisplayName("ListAllNoPegeable returns empty list of users when sucessful")
    void listAllNoPegeable_ReturnsEmptyListOfUsers_WhenSucessful(){
        BDDMockito.when(usersRepositoryMock.findAll())
                .thenReturn(Collections.emptyList());

        List<Users> users = userService.listAllNoPageable();

        Assertions.assertThat(users)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("FindById return a users when successful")
    void findById_ReturnsUsers_WhenSucessful(){

        Users usersExpected = UsersCreator.createUsersSaved();

        Users users = userService.findById(1L);

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.getId()).isNotNull();

        Assertions.assertThat(users).usingRecursiveComparison().isEqualTo(usersExpected);
    }

    @Test
    @DisplayName("FindById throws a NotFoundException when users not found")
    void findById_ThrowsNotFoundException_WhenUsersNotFound(){
        BDDMockito.when(usersRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.findById(1L));
    }

    @Test
    @DisplayName("Save and persist users when successful")
    void save_ReturnsUsersSaved_WhenSucessful(){
        BDDMockito.when(usersRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        BDDMockito.when(usersRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        Users usersExpected = UsersCreator.createUsersSaved();

        Users users = userService.save(UsersPostDtoCreator.createUsersPostDtoToBeSaved());

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.getId()).isNotNull();

        Assertions.assertThat(users).usingRecursiveComparison().isEqualTo(usersExpected);
    }

    @Test
    @DisplayName("Save throws a NotFoundException when an invalid language is reported")
    void save_ThrowsNotFoundException_WhenInvalidLanguage(){
        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(
                () -> userService.save(UsersPostDtoCreator.createUsersPostDtoToBeSaved()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Save throws a BadRequestException when an email is already registered in the system")
    void save_ThrowsBadRequestException_WhenEmailIsAlreadyRegistered(){

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.save(UsersPostDtoCreator.createUsersPostDtoToBeSaved()));

    }

    @Test
    @DisplayName("Save throws a BadRequestException when an cpf is already registered in the system")
    void save_ThrowsBadRequestException_WhenCpfIsAlreadyRegistered(){

        Assertions.assertThatThrownBy(() -> userService.save(UsersPostDtoCreator.createUsersPostDtoToBeSaved()))
                .isInstanceOf(BadRequestException.class);

    }

    @Test
    @DisplayName("Update and persist users when the user tries to change his own data")
    void update_ReturnsUsersUpdated_WhenChangeHisOwnData(){
        BDDMockito.when(usersRepositoryMock.save(ArgumentMatchers.any(Users.class)))
                .thenReturn(UsersCreator.createUsersUpdated());

        BDDMockito.when(usersRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UsersCreator.createUsersUpdated()));

        Users usersExpected = UsersCreator.createUsersUpdated();

        Users users = userService.update(UsersPutDtoCreator.createUsersPutDtoToBeUpdate(),UsersCreator.createUsersUpdated());

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.getId())
                .isNotNull()
                .isEqualTo(usersExpected.getId());

        Assertions.assertThat(users).usingRecursiveComparison().isEqualTo(usersExpected);
    }

    @Test
    @DisplayName("Update and persist users when the user is admin")
    void update_ReturnsUsersUpdated_WhenUserIsAdmin(){
        BDDMockito.when(usersRepositoryMock.save(ArgumentMatchers.any(Users.class)))
                .thenReturn(UsersCreator.createUsersUpdated());

        BDDMockito.when(usersRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UsersCreator.createUsersUpdated()));

        Users usersExpected = UsersCreator.createUsersUpdated();

        Users users = userService.update(UsersPutDtoCreator.createUsersPutDtoToBeUpdate(),UsersCreator.createUsersSavedAdmin());

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.getId())
                .isNotNull()
                .isEqualTo(usersExpected.getId());

        Assertions.assertThat(users).usingRecursiveComparison().isEqualTo(usersExpected);
    }

    @Test
    @DisplayName("Update throws a ForbiddenException when a user is not admin and tries to update another user")
    void update_ThrowsForbiddenException_WhenUseIsNotAdminAndUpdateAnotherUser(){
        Assertions.assertThatThrownBy(() ->
                userService.update(
                        UsersPutDtoCreator.createUsersPutDtoToBeUpdate(),
                        UsersCreator.createUsersUpdated()
                )).isInstanceOf(ForbiddenException.class);
    }

    @Test
    @DisplayName("Update throws a BadRequestException when an email is already registered in the system by another user")
    void update_ThrowsBadRequestException_WhenEmailIsAlreadyRegistered(){
        Assertions.assertThatThrownBy(() -> userService.update(
                    UsersPutDtoCreator.createUsersPutDtoToBeUpdate(),
                    UsersCreator.createUsersSavedAdmin()
                )).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Update throws a BadRequestException when an cpf is already registered in the system by another user")
    void update_ThrowsBadRequestException_WhenCpfIsAlreadyRegistered(){
        Assertions.assertThatThrownBy(() -> userService.update(
                UsersPutDtoCreator.createUsersPutDtoToBeUpdate(),
                UsersCreator.createUsersSavedAdmin()
        )).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("Update throws a NotFoundException when an invalid language is reported")
    void update_ThrowsNotFoundException_WhenInvalidLanguage(){
        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        BDDMockito.when(usersRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UsersCreator.createUsersUpdated()));

        Assertions.assertThatThrownBy(
                () -> userService.update(
                        UsersPutDtoCreator.createUsersPutDtoToBeUpdate(),
                        UsersCreator.createUsersUpdated()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Delete returns the active field to false when sucessful")
    void delete_ReturnsUsersDeleted_WhenSucessful(){
        BDDMockito.when(usersRepositoryMock.save(ArgumentMatchers.any(Users.class)))
                .thenReturn(UsersCreator.createUsersDeleted());

        Users usersExpected = UsersCreator.createUsersDeleted();

        Users users = userService.delete(1L);

        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.getId())
                .isNotNull()
                .isEqualTo(usersExpected.getId());

        Assertions.assertThat(users).usingRecursiveComparison().isEqualTo(usersExpected);
    }

    @Test
    @DisplayName("Delete throws NotFoundException when user not found")
    void delete_ThrowsNotFoundException_WhenUserNotTFound(){
        BDDMockito.when(usersRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> userService.delete(1L))
                .isInstanceOf(NotFoundException.class);
    }
}