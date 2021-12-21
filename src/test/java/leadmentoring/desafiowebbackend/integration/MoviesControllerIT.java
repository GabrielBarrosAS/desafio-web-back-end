package leadmentoring.desafiowebbackend.integration;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.repository.*;
import leadmentoring.desafiowebbackend.repository.MoviesRepository;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryCreator;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryPostDtoCreator;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryPutDtoCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesPostDtoCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesPutDtoCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesPostDtoCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesPutDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
@DisplayName("Integration Test for movies Controller")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MoviesControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private MoviesRepository moviesRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

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

    @BeforeEach
    void setUp(){

        Language language = languageRepository.save(LanguageCreator.createLanguageToBeSaved());
        CategoryCreator.LANGUAGE_CATEGORY = language;
        CategoryPostDtoCreator.LANGUAGE_POST_CATEGORY = language;
        CategoryPutDtoCreator.LANGUAGE_PUT_CATEGORY = language;

        MoviesCreator.LANGUAGE_MOVIES = language;
        MoviesPostDtoCreator.LANGUAGE_POST_MOVIES = language;
        MoviesPutDtoCreator.LANGUAGE_PUT_MOVIES = language;

        Category category = categoryRepository.save(CategoryCreator.createCategoryToBeSaved());

        MoviesCreator.CATEGORY_MOVIES = category;
        MoviesPostDtoCreator.CATEGORY_POST_MOVIES = category;
        MoviesPutDtoCreator.CATEGORY_PUT_MOVIES = category;
    }

    @Test
    @DisplayName("listAll returns list of Movies when successful")
    void listAll_ReturnsListOfMoviess_WhenSuccessful() {
        Movies moviesSaved = moviesRepository.save(MoviesCreator.createMoviesToBeSaved());

        List<Movies> moviesList = testRestTemplateRoleUser.exchange(
                "/movies",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Movies>>() {
                }).getBody();

        Assertions.assertThat(moviesList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(moviesList.get(0).getId()).isEqualTo(moviesSaved.getId());
        Assertions.assertThat(moviesList.get(0).getTittle()).isEqualTo(moviesSaved.getTittle());
    }

    @Test
    @DisplayName("listAll returns empty list of Movies not found")
    void listAll_ReturnsEmptyList_WhenMoviessNotFound() {

        List<Movies> moviesList = testRestTemplateRoleUser.exchange(
                "/movies",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Movies>>() {
                }).getBody();

        Assertions.assertThat(moviesList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findById returns Movies when successful")
    void findById_ReturnsMoviess_WhenSuccessful() {
        Movies moviesSaved = moviesRepository.save(MoviesCreator.createMoviesToBeSaved());

        Long expectedId = moviesSaved.getId();

        Movies movies = testRestTemplateRoleUser.getForObject("/movies/{id}", Movies.class, expectedId);

        Assertions.assertThat(movies).isNotNull();

        Assertions.assertThat(movies.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById returns 404 when moviem is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

        ResponseEntity<Movies> moviesResponseEntity = testRestTemplateRoleUser.exchange(
                "/movies/{id}",
                HttpMethod.GET,
                null,
                Movies.class,
                0);

        Assertions.assertThat(moviesResponseEntity)
                .isNotNull();
        Assertions.assertThat(moviesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("save and returns a new movie when successful")
    void save_ReturnsMovies_WhenSuccessful() {
        usersRepository.save(ADMIN);

        ResponseEntity<Movies> categoryResponseEntity = testRestTemplateRoleAdmin.postForEntity(
                "/movies",
                MoviesPostDtoCreator.createMoviesPostDtoToBeSaved(),
                Movies.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(categoryResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("save returns 403 when user is not admin")
    void save_Returns403_WhenUserIsNotAdmin() {
        usersRepository.save(USER);

        ResponseEntity<Movies> categoryResponseEntity = testRestTemplateRoleUser.postForEntity(
                "/movies",
                MoviesPostDtoCreator.createMoviesPostDtoToBeSaved(),
                Movies.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("save returns 401 when user not found (CASE 1)")
    void save_Returns401_WhenUserNotFoundCASE1() {
        usersRepository.save(USER);

        ResponseEntity<Movies> categoryResponseEntity = testRestTemplateRoleAdmin.postForEntity(
                "/movies",
                MoviesPostDtoCreator.createMoviesPostDtoToBeSaved(),
                Movies.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("save returns 401 when user not found (CASE 2)")
    void save_Returns401_WhenUserNotFoundCASE2() {
        usersRepository.save(ADMIN);

        ResponseEntity<Movies> categoryResponseEntity = testRestTemplateRoleUser.postForEntity(
                "/movies",
                MoviesPostDtoCreator.createMoviesPostDtoToBeSaved(),
                Movies.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("replace updates Movies when successful")
    void replace_UpdatesMovies_WhenSuccessful() {
        Movies savedMovies = moviesRepository.save(MoviesCreator.createMoviesToBeSaved());
        usersRepository.save(ADMIN);

        ResponseEntity<Movies> MoviesResponseEntity = testRestTemplateRoleAdmin.exchange(
                "/movies",
                HttpMethod.PUT,
                new HttpEntity<>(MoviesPutDtoCreator.createMoviesPutDtoToBeUpdate())
                , Movies.class);

        Assertions.assertThat(MoviesResponseEntity).isNotNull();

        Assertions.assertThat(MoviesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete Movies when successful")
    void delete_DeleteMovies_WhenSuccessful() {
        Movies savedMovies = moviesRepository.save(MoviesCreator.createMoviesToBeSaved());
        usersRepository.save(ADMIN);

        ResponseEntity<Movies> MoviesResponseEntity = testRestTemplateRoleAdmin.exchange(
                "/movies/{id}",
                HttpMethod.DELETE,
                null,
                Movies.class,
                savedMovies.getId());

        Assertions.assertThat(MoviesResponseEntity).isNotNull();

        Assertions.assertThat(MoviesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
