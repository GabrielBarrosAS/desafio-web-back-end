package leadmentoring.desafiowebbackend.repository;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.util.CategoryCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for the category repository")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @BeforeAll
    void setUp(){
        languageRepository.save(LanguageCreator.createLanguageSaved());
        categoryRepository.save(CategoryCreator.createCategoryToBeSaved());
    }



    @Test
    @DisplayName("Persists the new category when it occurs successfully")
    void save_PersistCategory_WhenSuccessful(){
        Category savedCategory = this.categoryRepository.findById(1L).get();

        Assertions.assertThat(savedCategory).isNotNull();
        Assertions.assertThat(savedCategory.getId()).isNotNull();
        Assertions.assertThat(savedCategory).isNotEqualTo(CategoryCreator.createCategorySaved());
    }

    @Test
    @DisplayName("Throws ConstraintViolationException when passing an invalid field")
    void save_ThrowsConstraintViolationException_WhenPassingAnInvalidField(){
        Category categoryToBeSavedNameNull = CategoryCreator.createCategoryToBeSaved();
        categoryToBeSavedNameNull.setName(null);
        Assertions.assertThatThrownBy(() -> this.categoryRepository.save(categoryToBeSavedNameNull))
                .isInstanceOf(ConstraintViolationException.class);

        Category categoryToBeSavedLanguageNull = CategoryCreator.createCategoryToBeSaved();
        categoryToBeSavedLanguageNull.setLanguage(null);
        Assertions.assertThatThrownBy(() -> this.categoryRepository.save(categoryToBeSavedLanguageNull))
                .isInstanceOf(ConstraintViolationException.class);

        Category categoryToBeSavedTagNull = CategoryCreator.createCategoryToBeSaved();
        categoryToBeSavedTagNull.setTag(null);
        Assertions.assertThatThrownBy(() -> this.categoryRepository.save(categoryToBeSavedTagNull))
                .isInstanceOf(ConstraintViolationException.class);

        Category categoryToBeSavedActiveNull = CategoryCreator.createCategoryToBeSaved();
        categoryToBeSavedActiveNull.setActive(null);
        Assertions.assertThatThrownBy(() -> this.categoryRepository.save(categoryToBeSavedActiveNull))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Return a category with id corresponding to the fetched")
    void findById_ReturnCategory_WhenCorrespondingId(){
        Category savedCategory = this.categoryRepository.findById(1L).get();

        Optional<Category> findByIdCategory = this.categoryRepository.findById(savedCategory.getId());

        Assertions.assertThat(findByIdCategory)
                .isNotEmpty()
                .isNotNull();
        Assertions.assertThat(findByIdCategory.get()).usingRecursiveComparison().isEqualTo(savedCategory);
    }

    @Test
    @DisplayName("Returns an empty Optional when ID does not match")
    void findById_ReturnEmptyOptional_WhenDoesNotCorrespondingId(){
        Optional<Category> findByIdCategory = this.categoryRepository.findById(0L);

        Assertions.assertThat(findByIdCategory )
                .isEmpty()
                .isNotNull();
    }

    @Test
    @DisplayName("Returns a list of all as category s stored in the database")
    void findAll_ReturnListCategorys_WhenThereAreCategorys(){

        Category savedCategoryOne = this.categoryRepository.findById(1L).get();

        List<Category> listCategory = this.categoryRepository.findAll();

        Assertions.assertThat(listCategory )
                .isNotNull()
                .isNotEmpty()
                .contains(savedCategoryOne)
                .hasSize(1);
    }

    @Test
    @DisplayName("Returns an empty list when there are no category s stored")
    void findAll_ReturnEmptyListCategorys_WhenThereAreNoCategorys(){
        List<Category> listCategory = this.categoryRepository.findAll();

        Assertions.assertThat(listCategory )
                .isNotNull()
                .isEmpty();
    }

}