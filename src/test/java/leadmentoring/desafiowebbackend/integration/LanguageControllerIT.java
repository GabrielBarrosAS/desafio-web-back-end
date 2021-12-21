package leadmentoring.desafiowebbackend.integration;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.repository.LanguageRepository;
import leadmentoring.desafiowebbackend.repository.UsersRepository;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguagePostDtoCreator;
import org.assertj.core.api.Assertions;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DisplayName("Integration Test for Language Controller")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LanguageControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private UsersRepository usersRepository;

    private static final Users USER = Users.builder()
            .name("User")
            .password("$2a$10$hSTIR1LEGbkA6US1B0IJVeoTsHrFKzPwXSeE40SvIFckopmMHoUTm")
            .email("user@user.com")
            .roles("ROLE_USER")
            .language(LanguageCreator.createLanguageSaved())
            .profile("URL PROFILE")
            .cpf("80380083086")
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
            .cpf("80380083086")
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

    @Test
    @DisplayName("listAll returns list of languages when successful")
    void listAll_ReturnsListOfLanguages_WhenSuccessful() {
        Language languageSaved = languageRepository.save(LanguageCreator.createLanguageToBeSaved());

        List<Language> languageList = testRestTemplateRoleUser.exchange(
                "/language",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Language>>() {
                }).getBody();

        Assertions.assertThat(languageList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(languageList.get(0).getId()).isEqualTo(languageSaved.getId());
        Assertions.assertThat(languageList.get(0).getName()).isEqualTo(languageSaved.getName());
        Assertions.assertThat(languageList.get(0).getTag()).isEqualTo(languageSaved.getTag());
    }

    @Test
    @DisplayName("listAll returns empty list of languages not found")
    void listAll_ReturnsEmptyList_WhenLanguagesNotFound() {

        List<Language> languageList = testRestTemplateRoleUser.exchange(
                "/language",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Language>>() {
                }).getBody();

        Assertions.assertThat(languageList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findById returns languages when successful")
    void findById_ReturnsLanguages_WhenSuccessful() {
        Language languageSaved = languageRepository.save(LanguageCreator.createLanguageToBeSaved());

        Long expectedId = languageSaved.getId();

        Language language = testRestTemplateRoleUser.getForObject("/language/{id}", Language.class, expectedId);

        Assertions.assertThat(language).isNotNull();

        Assertions.assertThat(language.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById returns 404 when language is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

        ResponseEntity<Language> languageResponseEntity = testRestTemplateRoleUser.exchange(
                "/language/{id}",
                HttpMethod.GET,
                null,
                Language.class,
                0);

        Assertions.assertThat(languageResponseEntity)
                .isNotNull();
        Assertions.assertThat(languageResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("save and returns a new language when successful")
    void save_ReturnsLanguage_WhenSuccessful() {
        Language languageSave = languageRepository.save(LanguageCreator.createLanguageSaved());
        usersRepository.save(ADMIN);

        ResponseEntity<Language> animeResponseEntity = testRestTemplateRoleAdmin.postForEntity(
                "/language",
                LanguagePostDtoCreator.createLanguagePostDtoToBeSaved(),
                Language.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("save returns 403 when user is not admin")
    void save_Returns403_WhenUserIsNotAdmin() {
        Language languageSave = languageRepository.save(LanguageCreator.createLanguageSaved());
        usersRepository.save(USER);

        ResponseEntity<Language> animeResponseEntity = testRestTemplateRoleUser.postForEntity(
                "/language",
                LanguagePostDtoCreator.createLanguagePostDtoToBeSaved(),
                Language.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("save returns 401 when user not found (CASE 1)")
    void save_Returns401_WhenUserNotFoundCASE1() {
        Language languageSave = languageRepository.save(LanguageCreator.createLanguageSaved());
        usersRepository.save(USER);

        ResponseEntity<Language> animeResponseEntity = testRestTemplateRoleAdmin.postForEntity(
                "/language",
                LanguagePostDtoCreator.createLanguagePostDtoToBeSaved(),
                Language.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("save returns 401 when user not found (CASE 2)")
    void save_Returns401_WhenUserNotFoundCASE2() {
        Language languageSave = languageRepository.save(LanguageCreator.createLanguageSaved());
        usersRepository.save(ADMIN);

        ResponseEntity<Language> animeResponseEntity = testRestTemplateRoleUser.postForEntity(
                "/language",
                LanguagePostDtoCreator.createLanguagePostDtoToBeSaved(),
                Language.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
