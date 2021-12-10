package leadmentoring.desafiowebbackend.repository;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.util.CategoryCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for the category repository")
@Log4j2
class CategoryRepositoryTest {

    private static Language language;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @BeforeEach
    void setLanguage(){
        language = languageRepository.save(LanguageCreator.createLanguageToBeSaved());
    }

    @Test
    @DisplayName("Persists the new category when it occurs successfully")
    void save_PersistCategory_WhenSuccessful(){

        Category categoryToBeSaved = CategoryCreator.createCategoryToBeSaved(language);
        Category savedCategory = categoryRepository.save(categoryToBeSaved);

        Assertions.assertThat(savedCategory).isNotNull();
        Assertions.assertThat(savedCategory.getId()).isNotNull();
        Assertions.assertThat(savedCategory).usingRecursiveComparison().isEqualTo(categoryToBeSaved);
    }

    @Test
    @DisplayName("Throws ConstraintViolationException when passing an invalid field")
    void save_ThrowsConstraintViolationException_WhenPassingAnInvalidField(){

        Category categoryToBeSavedNameNull = CategoryCreator.createCategoryToBeSaved(language);
        categoryToBeSavedNameNull.setName(null);
        Assertions.assertThatThrownBy(() -> categoryRepository.save(categoryToBeSavedNameNull))
                .isInstanceOf(ConstraintViolationException.class);

        Category categoryToBeSavedLanguageNull = CategoryCreator.createCategoryToBeSaved(language);
        categoryToBeSavedLanguageNull.setLanguage(null);
        Assertions.assertThatThrownBy(() -> categoryRepository.save(categoryToBeSavedLanguageNull))
                .isInstanceOf(ConstraintViolationException.class);

        Category categoryToBeSavedTagNull = CategoryCreator.createCategoryToBeSaved(language);
        categoryToBeSavedTagNull.setTag(null);
        Assertions.assertThatThrownBy(() -> categoryRepository.save(categoryToBeSavedTagNull))
                .isInstanceOf(ConstraintViolationException.class);

        Category categoryToBeSavedActiveNull = CategoryCreator.createCategoryToBeSaved(language);
        categoryToBeSavedActiveNull.setActive(null);
        Assertions.assertThatThrownBy(() -> categoryRepository.save(categoryToBeSavedActiveNull))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Return a category with id corresponding to the fetched")
    void findById_ReturnCategory_WhenCorrespondingId(){

        Category savedCategory = categoryRepository.save(CategoryCreator.createCategoryToBeSaved(language));

        Optional<Category> findByIdCategory = categoryRepository.findById(savedCategory.getId());

        Assertions.assertThat(findByIdCategory)
                .isNotEmpty()
                .isNotNull();
        Assertions.assertThat(findByIdCategory.get()).usingRecursiveComparison().isEqualTo(savedCategory);
    }

    @Test
    @DisplayName("Returns an empty Optional when ID does not match")
    void findById_ReturnEmptyOptional_WhenDoesNotCorrespondingId(){
        Optional<Category> findByIdCategory = categoryRepository.findById(0L);

        Assertions.assertThat(findByIdCategory )
                .isEmpty()
                .isNotNull();
    }

    @Test
    @DisplayName("Returns a list of all as category s stored in the database")
    void findAll_ReturnListCategorys_WhenThereAreCategorys(){

        Category categoryToBeSavedOne = CategoryCreator.createCategoryToBeSaved(language);
        Category categoryToBeSavedTwo = CategoryCreator.createCategoryToBeSaved(language);

        Category savedCategoryOne = categoryRepository.save(categoryToBeSavedOne);
        Category savedCategoryTwo = categoryRepository.save(categoryToBeSavedTwo);

        List<Category> listCategory = categoryRepository.findAll();

        Assertions.assertThat(listCategory )
                .isNotNull()
                .isNotEmpty()
                .contains(savedCategoryOne)
                .contains(savedCategoryTwo)
                .hasSize(2);
    }

    @Test
    @DisplayName("Returns an empty list when there are no category s stored")
    void findAll_ReturnEmptyListCategorys_WhenThereAreNoCategorys(){
        List<Category > listCategory = categoryRepository.findAll();

        Assertions.assertThat(listCategory )
                .isNotNull()
                .isEmpty();
    }

}