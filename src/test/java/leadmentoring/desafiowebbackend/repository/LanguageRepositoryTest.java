package leadmentoring.desafiowebbackend.repository;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.util.LanguageCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for the language repository")
class LanguageRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    @DisplayName("Throws ConstraintViolationException when passing an invalid field")
    void save_ThrowsConstraintViolationException_WhenPassingAnInvalidField(){

        Language languageToBeSavedNameNull = LanguageCreator.createLanguageToBeSaved();
        languageToBeSavedNameNull.setName(null);

        Assertions.assertThatThrownBy(() -> this.languageRepository.save(languageToBeSavedNameNull))
                .isInstanceOf(ConstraintViolationException.class);

        Language languageToBeSavedTagNull = LanguageCreator.createLanguageToBeSaved();
        languageToBeSavedTagNull.setTag(null);

        Assertions.assertThatThrownBy(() -> this.languageRepository.save(languageToBeSavedTagNull))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Persists the new language when it occurs successfully")
    void save_PersistLanguage_WhenSuccessful(){
        Language languageToBeSaved = LanguageCreator.createLanguageToBeSaved();

        Language savedLanguage = this.languageRepository.save(languageToBeSaved);

        Assertions.assertThat(savedLanguage).isNotNull();
        Assertions.assertThat(savedLanguage.getId()).isNotNull();
        Assertions.assertThat(savedLanguage).usingRecursiveComparison().isEqualTo(languageToBeSaved);
    }

    @Test
    @DisplayName("Return a language with id corresponding to the fetched")
    void findById_ReturnLanguage_WhenCorrespondingId(){
        Language savedLanguage = this.languageRepository.save(LanguageCreator.createLanguageToBeSaved());

        Optional<Language> findByIdLanguage = this.languageRepository.findById(savedLanguage.getId());

        Assertions.assertThat(findByIdLanguage)
                .isNotEmpty()
                .isNotNull();
        Assertions.assertThat(findByIdLanguage.get()).usingRecursiveComparison().isEqualTo(savedLanguage);
    }

    @Test
    @DisplayName("Returns an empty Optional when ID does not match")
    void findById_ReturnEmptyOptional_WhenDoesNotCorrespondingId(){
        Optional<Language> findByIdLanguage = this.languageRepository.findById(0L);

        Assertions.assertThat(findByIdLanguage)
                .isEmpty()
                .isNotNull();
    }

    @Test
    @DisplayName("Returns a list of all as languages stored in the database")
    void findAll_ReturnListLanguages_WhenThereAreLanguages(){
        languageRepository.deleteAll();
        Language savedLanguageOne = this.languageRepository.save(LanguageCreator.createLanguageToBeSaved());
        Language savedLanguageTwo = this.languageRepository.save(LanguageCreator.createLanguageToBeSaved());

        List<Language> listLanguage = this.languageRepository.findAll();

        Assertions.assertThat(listLanguage)
                .isNotNull()
                .isNotEmpty()
                .contains(savedLanguageOne)
                .contains(savedLanguageTwo)
                .hasSize(2);
    }

    @Test
    @DisplayName("Returns an empty list when there are no languages stored")
    void findAll_ReturnEmptyListLanguages_WhenThereAreNoLanguages(){
        languageRepository.deleteAll();
        List<Language> listLanguage = this.languageRepository.findAll();

        Assertions.assertThat(listLanguage)
                .isNotNull()
                .isEmpty();
    }
}