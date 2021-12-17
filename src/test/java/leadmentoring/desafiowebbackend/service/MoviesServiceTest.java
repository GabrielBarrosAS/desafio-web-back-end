package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundException;
import leadmentoring.desafiowebbackend.repository.MoviesRepository;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryCreator;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryPostDtoCreator;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryPutDtoCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesPostDtoCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesPutDtoCreator;
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
class MoviesServiceTest {
    @InjectMocks
    private MoviesService moviesService;

    @Mock
    private MoviesRepository moviesRepositoryMock;

    @Mock
    private LanguageService languageServiceMock;

    @Mock
    private CategoryService categoryServiceMock;

    @BeforeAll
    void setUpAll(){

        //Não funciona
        //Language language = LanguageCreator.createLanguageSaved();
        //Category category = CategoryCreator.createCategorySaved();

        CategoryCreator.LANGUAGE_CATEGORY = LanguageCreator.createLanguageSaved();
        CategoryPostDtoCreator.LANGUAGE_POST_CATEGORY = LanguageCreator.createLanguageSaved();
        CategoryPutDtoCreator.LANGUAGE_PUT_CATEGORY = LanguageCreator.createLanguageSaved();

        MoviesCreator.LANGUAGE_MOVIES = LanguageCreator.createLanguageSaved();
        MoviesPostDtoCreator.LANGUAGE_POST_MOVIES = LanguageCreator.createLanguageSaved();
        MoviesPutDtoCreator.LANGUAGE_PUT_MOVIES = LanguageCreator.createLanguageSaved();

        MoviesCreator.CATEGORY_MOVIES = CategoryCreator.createCategorySaved();
        MoviesPostDtoCreator.CATEGORY_POST_MOVIES = CategoryCreator.createCategorySaved();
        MoviesPutDtoCreator.CATEGORY_PUT_MOVIES = CategoryCreator.createCategorySaved();
    }

    @AfterAll
    void toClear(){
        MoviesCreator.LANGUAGE_MOVIES = null;
        MoviesPostDtoCreator.LANGUAGE_POST_MOVIES = null;
        MoviesPutDtoCreator.LANGUAGE_PUT_MOVIES = null;

        MoviesCreator.CATEGORY_MOVIES = null;
        MoviesPostDtoCreator.CATEGORY_POST_MOVIES = null;
        MoviesPutDtoCreator.CATEGORY_PUT_MOVIES = null;

        CategoryCreator.LANGUAGE_CATEGORY = null;
        CategoryPostDtoCreator.LANGUAGE_POST_CATEGORY = null;
        CategoryPutDtoCreator.LANGUAGE_PUT_CATEGORY = null;
    }

    @BeforeEach
    void setUp(){
        BDDMockito.when(moviesRepositoryMock.findAll())
                .thenReturn(List.of(MoviesCreator.createMoviesSaved()));

        BDDMockito.when(moviesRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(MoviesCreator.createMoviesSaved()));

        BDDMockito.when(moviesRepositoryMock.save(ArgumentMatchers.any(Movies.class)))
                .thenReturn(MoviesCreator.createMoviesSaved());

        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(LanguageCreator.createLanguageSaved());

        BDDMockito.when(categoryServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(CategoryCreator.createCategorySaved());
    }

    @Test
    @DisplayName("ListAllNoPegeable returns list of moviess when sucessful")
    void listAllNoPegeable_ReturnsListOfMoviess_WhenSucessful(){

        Movies moviesExpected = MoviesCreator.createMoviesSaved();

        List<Movies> movies = moviesService.listAllNoPageable();

        Assertions.assertThat(movies)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(movies.get(0)).usingRecursiveComparison().isEqualTo(moviesExpected);

    }

    @Test
    @DisplayName("ListAllNoPegeable returns empty list of movies when sucessful")
    void listAllNoPegeable_ReturnsEmptyListOfMovies_WhenSucessful(){
        BDDMockito.when(moviesRepositoryMock.findAll())
                .thenReturn(Collections.emptyList());

        List<Movies> movies = moviesService.listAllNoPageable();

        Assertions.assertThat(movies)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("FindById return a movies when successful")
    void findById_ReturnsMovies_WhenSucessful(){

        Movies moviesExpected = MoviesCreator.createMoviesSaved();

        Movies movies = moviesService.findById(1L);

        Assertions.assertThat(movies).isNotNull();
        Assertions.assertThat(movies.getId()).isNotNull();

        Assertions.assertThat(movies).usingRecursiveComparison().isEqualTo(moviesExpected);
    }

    @Test
    @DisplayName("FindById throws a NotFoundException when movies not found")
    void findById_ThrowsNotFoundException_WhenMoviesNotFound(){
        BDDMockito.when(moviesRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> moviesService.findById(1L));
    }

    @Test
    @DisplayName("Save and persist movies when successful")
    void save_ReturnsMoviesSaved_WhenSucessful(){

        Movies moviesExpected = MoviesCreator.createMoviesSaved();

        Movies movies = moviesService.save(MoviesPostDtoCreator.createMoviesPostDtoToBeSaved());

        Assertions.assertThat(movies).isNotNull();
        Assertions.assertThat(movies.getId()).isNotNull();

        Assertions.assertThat(movies).usingRecursiveComparison().isEqualTo(moviesExpected);
    }

    @Test
    @DisplayName("Save throws a NotFoundException when an invalid language is reported")
    void save_ThrowsNotFoundException_WhenInvalidLanguage(){
        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(
                        () -> moviesService.save(MoviesPostDtoCreator.createMoviesPostDtoToBeSaved()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Save throws a NotFoundException when an invalid category is reported")
    void save_ThrowsNotFoundException_WhenInvalidCategory(){
        BDDMockito.when(categoryServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(
                        () -> moviesService.save(MoviesPostDtoCreator.createMoviesPostDtoToBeSaved()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Update and persist movies when successful")
    void update_ReturnsMoviesUpdated_WhenSucessful(){
        BDDMockito.when(moviesRepositoryMock.save(ArgumentMatchers.any(Movies.class)))
                .thenReturn(MoviesCreator.createMoviesUpdated());

        Movies moviesExpected = MoviesCreator.createMoviesUpdated();

        Movies movies = moviesService.update(MoviesPutDtoCreator.createMoviesPutDtoToBeUpdate());

        Assertions.assertThat(movies).isNotNull();
        Assertions.assertThat(movies.getId())
                .isNotNull()
                .isEqualTo(moviesExpected.getId());

        Assertions.assertThat(movies).usingRecursiveComparison().isEqualTo(moviesExpected);
    }

    @Test
    @DisplayName("Update throws a NotFoundException when an invalid language is reported")
    void update_ThrowsNotFoundException_WhenInvalidLanguage(){
        BDDMockito.when(languageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(
                        () -> moviesService.update(MoviesPutDtoCreator.createMoviesPutDtoToBeUpdate()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Update throws a NotFoundException when an invalid category is reported")
    void update_ThrowsNotFoundException_WhenInvalidCategory(){
        BDDMockito.when(categoryServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(
                        () -> moviesService.update(MoviesPutDtoCreator.createMoviesPutDtoToBeUpdate()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Delete the active field to false when sucessful")
    void delete_ReturnsMoviesDeleted_WhenSucessful(){
        BDDMockito.when(moviesRepositoryMock.save(ArgumentMatchers.any(Movies.class)))
                .thenReturn(MoviesCreator.createMoviesDeleted());

        Movies moviesExpected = MoviesCreator.createMoviesDeleted();

        Movies movies = moviesService.delete(1L);

        Assertions.assertThat(movies).isNotNull();
        Assertions.assertThat(movies.getId())
                .isNotNull()
                .isEqualTo(moviesExpected.getId());

        Assertions.assertThat(movies).usingRecursiveComparison().isEqualTo(moviesExpected);
    }

    @Test
    @DisplayName("Delete throws NotFoundException when movie not found")
    void delete_ThrowsNotFoundException_WhenMovieNotTFound(){
        BDDMockito.when(moviesRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new NotFoundException("")).thenReturn(null);

        Assertions.assertThatThrownBy(() -> moviesService.delete(1L))
                .isInstanceOf(NotFoundException.class);
    }
}