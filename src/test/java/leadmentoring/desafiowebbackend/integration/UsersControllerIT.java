package leadmentoring.desafiowebbackend.integration;

import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.repository.LanguageRepository;
import leadmentoring.desafiowebbackend.repository.UsersRepository;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
import leadmentoring.desafiowebbackend.util.UsersCreator.UsersCreator;
import leadmentoring.desafiowebbackend.util.UsersCreator.UsersPostDtoCreator;
import leadmentoring.desafiowebbackend.util.UsersCreator.UsersPutDtoCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DisplayName("Integration Test for Users Controller")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Log4j2
class UsersControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LanguageRepository languageRepository;

    private static final Users USER = Users.builder()
            .name("User")
            .password("$2a$10$hSTIR1LEGbkA6US1B0IJVeoTsHrFKzPwXSeE40SvIFckopmMHoUTm")
            .email("user@user.com")
            .roles("ROLE_USER")
            .language(LanguageCreator.createLanguageSaved())
            .profile("URL PROFILE")
            .cpf("49555444013")
            .telephone("888888888")
            .active(true)
            .build();

    private static final Users ADMIN = Users.builder()
            .name("Admin")
            .password("$2a$10$hSTIR1LEGbkA6US1B0IJVeoTsHrFKzPwXSeE40SvIFckopmMHoUTm")
            .email("admin@admin.com")
            .roles("ROLE_USER,ROLE_ADMIN")
            .language(LanguageCreator.createLanguageSaved())
            .profile("URL PROFILE")
            .cpf("49555444013")
            .telephone("888888888")
            .active(true)
            .build();
    
    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("user@user.com", "academy");
            return new TestRestTemplate(restTemplateBuilder);
        }
        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("admin@admin.com", "academy");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @BeforeEach
    void setUp(){
        UsersCreator.LANGUAGE_USERS = languageRepository.save(LanguageCreator.createLanguageSaved());
        UsersPostDtoCreator.LANGUAGE_POST_USERS = languageRepository.save(LanguageCreator.createLanguageSaved());
        UsersPutDtoCreator.LANGUAGE_PUT_USERS = languageRepository.save(LanguageCreator.createLanguageSaved());
    }

    @Test
    @DisplayName("listAll returns list of userss when successful")
    void listAll_ReturnsListOfUserss_WhenSuccessful() {
        Users usersSaved = usersRepository.save(UsersCreator.createUsersToBeSaved());
        usersRepository.save(ADMIN);
        List<Users> usersList = testRestTemplateRoleAdmin.exchange(
                "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Users>>() {
                }).getBody();

        Assertions.assertThat(usersList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(usersList.get(0).getId()).isEqualTo(usersSaved.getId());
        Assertions.assertThat(usersList.get(0).getName()).isEqualTo(usersSaved.getName());
    }

    @Test
    @DisplayName("findById returns users when successful")
    void findById_ReturnsUserss_WhenSuccessful() {
        Users usersSaved = usersRepository.save(ADMIN);

        Long expectedId = usersSaved.getId();

        Users users = testRestTemplateRoleAdmin.getForObject("/users/{id}", Users.class, expectedId);

        Assertions.assertThat(users).isNotNull();

        Assertions.assertThat(users.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById returns 404 when users is not found")
    void findById_ReturnsEmptyListOfUsers_WhenAnimeIsNotFound() {
        usersRepository.save(ADMIN);
        ResponseEntity<Users> usersResponseEntity = testRestTemplateRoleAdmin.exchange(
                "/users/{id}",
                HttpMethod.GET,
                null,
                Users.class,
                0);

        Assertions.assertThat(usersResponseEntity)
                .isNotNull();
        Assertions.assertThat(usersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("save and returns a new users when successful")
    void save_ReturnsUsers_WhenSuccessful() {

        ResponseEntity<Users> usersResponseEntity = testRestTemplateRoleUser.postForEntity(
                "/users",
                UsersPostDtoCreator.createUsersPostDtoToBeSaved(),
                Users.class);

        Assertions.assertThat(usersResponseEntity).isNotNull();
        Assertions.assertThat(usersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(usersResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(usersResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("replace updates users when successful")
    void replace_UpdatesUsers_WhenSuccessful() {
        usersRepository.save(USER);

        ResponseEntity<Users> usersResponseEntity = testRestTemplateRoleUser.exchange(
                "/users",
                HttpMethod.PUT,
                new HttpEntity<>(UsersPutDtoCreator.createUsersPutDtoToBeUpdate())
                , Users.class);

        Assertions.assertThat(usersResponseEntity).isNotNull();

        Assertions.assertThat(usersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete users when successful")
    void delete_DeleteUsers_WhenSuccessful() {
        Users savedUsers = usersRepository.save(UsersCreator.createUsersToBeSaved());
        usersRepository.save(ADMIN);

        ResponseEntity<Users> usersResponseEntity = testRestTemplateRoleAdmin.exchange(
                "/users/{id}",
                HttpMethod.DELETE,
                null,
                Users.class,
                savedUsers.getId());

        Assertions.assertThat(usersResponseEntity).isNotNull();

        Assertions.assertThat(usersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
