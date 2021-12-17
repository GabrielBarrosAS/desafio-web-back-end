package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundException;
import leadmentoring.desafiowebbackend.repository.CategoryRepository;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryCreator;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryPostDtoCreator;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryPutDtoCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @Mock
    private LanguageService languageServiceMock;

    @BeforeAll
    void setUpAll(){
        CategoryCreator.LANGUAGE_CATEGORY = LanguageCreator.createLanguageSaved();
        CategoryPostDtoCreator.LANGUAGE_POST_CATEGORY = LanguageCreator.createLanguageSaved();
        CategoryPutDtoCreator.LANGUAGE_PUT_CATEGORY = LanguageCreator.createLanguageSaved();
    }

    @AfterAll
    void toClear(){
        CategoryCreator.LANGUAGE_CATEGORY = null;
        CategoryPostDtoCreator.LANGUAGE_POST_CATEGORY = null;
        CategoryPutDtoCreator.LANGUAGE_PUT_CATEGORY = null;
    }

    @BeforeEach
    void setUp(){
        BDDMockito.when(categoryRepositoryMock.findAll())
                .thenReturn(List.of(CategoryCreator.createCategorySaved()));

        BDDMockito.when(categoryRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CategoryCreator.createCategorySaved()));

        BDDMockito.when(categoryRepositoryMock.save(ArgumentMatchers.any(Category.class)))
                .thenReturn(CategoryCreator.createCategorySaved());

        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(LanguageCreator.createLanguageSaved());
    }

    @Test
    @DisplayName("ListAllNoPegeable returns list of categorys when sucessful")
    void listAllNoPegeable_ReturnsListOfCategorys_WhenSucessful(){

        Category categoryExpected = CategoryCreator.createCategorySaved();

        List<Category> categorys = categoryService.listAllNoPageable();

        Assertions.assertThat(categorys)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(categorys.get(0)).usingRecursiveComparison().isEqualTo(categoryExpected);

    }

    @Test
    @DisplayName("ListAllNoPegeable returns empty list of movies when sucessful")
    void listAllNoPegeable_ReturnsEmptyListOfUsers_WhenSucessful(){
        BDDMockito.when(categoryRepositoryMock.findAll())
                .thenReturn(Collections.emptyList());

        List<Category> categories = categoryService.listAllNoPageable();

        Assertions.assertThat(categories)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("FindById return a category when successful")
    void findById_ReturnsCategory_WhenSucessful(){

        Category categoryExpected = CategoryCreator.createCategorySaved();

        Category category = categoryService.findById(1L);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isNotNull();

        Assertions.assertThat(category).usingRecursiveComparison().isEqualTo(categoryExpected);
    }

    @Test
    @DisplayName("FindById throws a NotFoundException when category not found")
    void findById_ThrowsNotFoundException_WhenCategoryNotFound(){
        BDDMockito.when(categoryRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> categoryService.findById(1L));
    }

    @Test
    @DisplayName("Save and persist category when successful")
    void save_ReturnsCategorySaved_WhenSucessful(){

        Category categoryExpected = CategoryCreator.createCategorySaved();

        Category category = categoryService.save(CategoryPostDtoCreator.createCategoryPostDtoToBeSaved());

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId()).isNotNull();

        Assertions.assertThat(category).usingRecursiveComparison().isEqualTo(categoryExpected);
    }

    @Test
    @DisplayName("Save throws a NotFoundException when an invalid language is reported")
    void save_ThrowsNotFoundException_WhenInvalidLanguage(){
        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(
                        () -> categoryService.save(CategoryPostDtoCreator.createCategoryPostDtoToBeSaved()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Update and persist category when successful")
    void update_ReturnsCategoryUpdated_WhenSucessful(){
        BDDMockito.when(categoryRepositoryMock.save(ArgumentMatchers.any(Category.class)))
                .thenReturn(CategoryCreator.createCategoryUpdated());

        Category categoryExpected = CategoryCreator.createCategoryUpdated();

        Category category = categoryService.update(CategoryPutDtoCreator.createCategoryPutDtoToBeUpdate());

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId())
                .isNotNull()
                .isEqualTo(categoryExpected.getId());

        Assertions.assertThat(category).usingRecursiveComparison().isEqualTo(categoryExpected);
    }

    @Test
    @DisplayName("Update throws a NotFoundException when an invalid language is reported")
    void update_ThrowsNotFoundException_WhenInvalidLanguage(){
        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> categoryService.update(CategoryPutDtoCreator.createCategoryPutDtoToBeUpdate()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Delete the active field to false when sucessful")
    void delete_ReturnsCategoryDeleted_WhenSucessful(){
        BDDMockito.when(categoryRepositoryMock.save(ArgumentMatchers.any(Category.class)))
                .thenReturn(CategoryCreator.createCategoryDeleted());

        Category categoryExpected = CategoryCreator.createCategoryDeleted();

        Category category = categoryService.delete(1L);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getId())
                .isNotNull()
                .isEqualTo(categoryExpected.getId());

        Assertions.assertThat(category).usingRecursiveComparison().isEqualTo(categoryExpected);
    }

    @Test
    @DisplayName("Delete throws NotFoundException when user not found")
    void delete_ThrowsNotFoundException_WhenUserNotTFound(){
        BDDMockito.when(categoryRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> categoryService.delete(1L))
                .isInstanceOf(NotFoundException.class);
    }
}