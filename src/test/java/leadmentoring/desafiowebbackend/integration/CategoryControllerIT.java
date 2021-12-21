package leadmentoring.desafiowebbackend.integration;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Users;
import leadmentoring.desafiowebbackend.repository.CategoryRepository;
import leadmentoring.desafiowebbackend.repository.LanguageRepository;
import leadmentoring.desafiowebbackend.repository.UsersRepository;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryCreator;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryPostDtoCreator;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryPutDtoCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
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
@DisplayName("Integration Test for Category Controller")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

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
        CategoryCreator.LANGUAGE_CATEGORY = languageRepository.save(LanguageCreator.createLanguageSaved());
        CategoryPostDtoCreator.LANGUAGE_POST_CATEGORY = languageRepository.save(LanguageCreator.createLanguageSaved());
        CategoryPutDtoCreator.LANGUAGE_PUT_CATEGORY = languageRepository.save(LanguageCreator.createLanguageSaved());
    }

    @Test
    @DisplayName("listAll returns list of categorys when successful")
    void listAll_ReturnsListOfCategorys_WhenSuccessful() {
        Category categorySaved = categoryRepository.save(CategoryCreator.createCategoryToBeSaved());

        List<Category> categoryList = testRestTemplateRoleUser.exchange(
                "/category",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {
                }).getBody();

        Assertions.assertThat(categoryList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(categoryList.get(0).getId()).isEqualTo(categorySaved.getId());
        Assertions.assertThat(categoryList.get(0).getName()).isEqualTo(categorySaved.getName());
        Assertions.assertThat(categoryList.get(0).getTag()).isEqualTo(categorySaved.getTag());
    }

    @Test
    @DisplayName("listAll returns empty list of categorys not found")
    void listAll_ReturnsEmptyList_WhenCategorysNotFound() {

        List<Category> categoryList = testRestTemplateRoleUser.exchange(
                "/category",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {
                }).getBody();

        Assertions.assertThat(categoryList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findById returns categorys when successful")
    void findById_ReturnsCategorys_WhenSuccessful() {
        Category categorySaved = categoryRepository.save(CategoryCreator.createCategoryToBeSaved());

        Long expectedId = categorySaved.getId();

        Category category = testRestTemplateRoleUser.getForObject("/category/{id}", Category.class, expectedId);

        Assertions.assertThat(category).isNotNull();

        Assertions.assertThat(category.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById returns 404 when category is not found")
    void findById_ReturnsEmptyListOfCategory_WhenAnimeIsNotFound() {

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleUser.exchange(
                "/category/{id}",
                HttpMethod.GET,
                null,
                Category.class,
                0);

        Assertions.assertThat(categoryResponseEntity)
                .isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("save and returns a new category when successful")
    void save_ReturnsCategory_WhenSuccessful() {
        usersRepository.save(ADMIN);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleAdmin.postForEntity(
                "/category",
                CategoryPostDtoCreator.createCategoryPostDtoToBeSaved(),
                Category.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(categoryResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("save returns 403 when user is not admin")
    void save_Returns403_WhenUserIsNotAdmin() {
        usersRepository.save(USER);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleUser.postForEntity(
                "/category",
                CategoryPostDtoCreator.createCategoryPostDtoToBeSaved(),
                Category.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("save returns 401 when user not found (CASE 1)")
    void save_Returns401_WhenUserNotFoundCASE1() {
        usersRepository.save(USER);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleAdmin.postForEntity(
                "/category",
                CategoryPostDtoCreator.createCategoryPostDtoToBeSaved(),
                Category.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("save returns 401 when user not found (CASE 2)")
    void save_Returns401_WhenUserNotFoundCASE2() {
        usersRepository.save(ADMIN);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleUser.postForEntity(
                "/category",
                CategoryPostDtoCreator.createCategoryPostDtoToBeSaved(),
                Category.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("replace updates category when successful")
    void replace_UpdatesCategory_WhenSuccessful() {
        Category savedCategory = categoryRepository.save(CategoryCreator.createCategoryToBeSaved());
        usersRepository.save(ADMIN);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleAdmin.exchange(
                "/category",
                HttpMethod.PUT,
                new HttpEntity<>(CategoryPutDtoCreator.createCategoryPutDtoToBeUpdate())
                , Category.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();

        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete category when successful")
    void delete_DeleteCategory_WhenSuccessful() {
        Category savedCategory = categoryRepository.save(CategoryCreator.createCategoryToBeSaved());
        usersRepository.save(ADMIN);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleAdmin.exchange(
                "/category/{id}",
                HttpMethod.DELETE,
                null,
                Category.class,
                savedCategory.getId());

        Assertions.assertThat(categoryResponseEntity).isNotNull();

        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
