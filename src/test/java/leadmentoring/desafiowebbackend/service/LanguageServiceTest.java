package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundException;
import leadmentoring.desafiowebbackend.repository.LanguageRepository;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguagePostDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class LanguageServiceTest {
    @InjectMocks
    private LanguageService languageService;

    @Mock
    private LanguageRepository languageRepositoryMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(languageRepositoryMock.findAll())
                .thenReturn(List.of(LanguageCreator.createLanguageSaved()));

        BDDMockito.when(languageRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(LanguageCreator.createLanguageSaved()));

        BDDMockito.when(languageRepositoryMock.save(ArgumentMatchers.any(Language.class)))
                .thenReturn(LanguageCreator.createLanguageSaved());
    }

    @Test
    @DisplayName("ListAllNoPegeable returns list of languages when sucessful")
    void listAllNoPegeable_ReturnsListOfLanguages_WhenSucessful(){

        Language languageExpected = LanguageCreator.createLanguageSaved();

        List<Language> languages = languageService.listAllNoPageable();

        Assertions.assertThat(languages)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(languages.get(0)).usingRecursiveComparison().isEqualTo(languageExpected);

    }

    @Test
    @DisplayName("FindById return a language when successful")
    void findById_ReturnsLanguage_WhenSucessful(){

        Language languageExpected = LanguageCreator.createLanguageSaved();

        Language language = languageService.findById(1L);

        Assertions.assertThat(language).isNotNull();
        Assertions.assertThat(language.getId()).isNotNull();

        Assertions.assertThat(language).usingRecursiveComparison().isEqualTo(languageExpected);
    }

    @Test
    @DisplayName("FindById throws a NotFoundException when language not found")
    void findById_ThrowsNotFoundException_WhenLanguageNotFound(){
        BDDMockito.when(languageRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> languageService.findById(1L));
    }

    @Test
    @DisplayName("Save and persist language when successful")
    void save_ReturnsLanguageSaved_WhenSucessful(){

        Language languageExpected = LanguageCreator.createLanguageSaved();

        Language language = languageService.save(LanguagePostDtoCreator.createLanguagePostDtoToBeSaved());

        Assertions.assertThat(language).isNotNull();
        Assertions.assertThat(language.getId()).isNotNull();

        Assertions.assertThat(language).usingRecursiveComparison().isEqualTo(languageExpected);
    }
}