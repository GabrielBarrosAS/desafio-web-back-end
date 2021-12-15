package leadmentoring.desafiowebbackend.repository;

import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.util.CategoryCreator.CategoryCreator;
import leadmentoring.desafiowebbackend.util.LanguageCreator.LanguageCreator;
import leadmentoring.desafiowebbackend.util.MoviesCreator.MoviesCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for the movies repository")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Log4j2
class MoviesRepositoryTest {
    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    void setUp(){
        MoviesCreator.LANGUAGE_MOVIES = languageRepository.save(LanguageCreator.createLanguageToBeSaved());
        CategoryCreator.LANGUAGE_CATEGORY = MoviesCreator.LANGUAGE_MOVIES;
        MoviesCreator.CATEGORY_MOVIES = categoryRepository.save(CategoryCreator.createCategoryToBeSaved());
    }

    @BeforeEach
    void show(){
        List<Movies> all = moviesRepository.findAll();
    }

    @AfterAll
    void toClear(){
        categoryRepository.deleteById(MoviesCreator.CATEGORY_MOVIES.getId());
        languageRepository.deleteById(MoviesCreator.LANGUAGE_MOVIES.getId());
        MoviesCreator.CATEGORY_MOVIES = null;
        MoviesCreator.LANGUAGE_MOVIES = null;
        CategoryCreator.LANGUAGE_CATEGORY = null;
    }

    @Test
    @DisplayName("Persists the new movies when it occurs successfully")
    void save_PersistMovies_WhenSuccessful(){
        Movies moviesToBeSaved = MoviesCreator.createMoviesToBeSaved();
        Movies savedMovies = moviesRepository.save(moviesToBeSaved);

        Assertions.assertThat(savedMovies).isNotNull();
        Assertions.assertThat(savedMovies.getId()).isNotNull();
    }

    @Test
    @DisplayName("Throws ConstraintViolationException when passing an invalid field")
    void save_ThrowsConstraintViolationException_WhenPassingAnInvalidField(){

        Movies userToBeSavedTittleNull = MoviesCreator.createMoviesToBeSaved();
        userToBeSavedTittleNull.setTittle(null);
        Assertions.assertThatThrownBy(() -> this.moviesRepository.save(userToBeSavedTittleNull))
                .isInstanceOf(ConstraintViolationException.class);

        Movies userToBeSavedLanguageNull = MoviesCreator.createMoviesToBeSaved();
        userToBeSavedLanguageNull.setLanguage(null);
        Assertions.assertThatThrownBy(() -> this.moviesRepository.save(userToBeSavedLanguageNull))
                .isInstanceOf(ConstraintViolationException.class);

        Movies userToBeSavedCategoryNull = MoviesCreator.createMoviesToBeSaved();
        userToBeSavedCategoryNull.setCategory(null);
        Assertions.assertThatThrownBy(() -> this.moviesRepository.save(userToBeSavedCategoryNull))
                .isInstanceOf(ConstraintViolationException.class);

        Movies userToBeSavedActiveNull = MoviesCreator.createMoviesToBeSaved();
        userToBeSavedActiveNull.setActive(null);
        Assertions.assertThatThrownBy(() -> this.moviesRepository.save(userToBeSavedActiveNull))
                .isInstanceOf(ConstraintViolationException.class);

        Movies userToBeSavedSynopsisNull = MoviesCreator.createMoviesToBeSaved();
        userToBeSavedSynopsisNull.setSynopsis(null);
        Assertions.assertThatThrownBy(() -> this.moviesRepository.save(userToBeSavedSynopsisNull))
                .isInstanceOf(ConstraintViolationException.class);

        Movies userToBeSavedLaunchDataNull = MoviesCreator.createMoviesToBeSaved();
        userToBeSavedLaunchDataNull.setLaunchData(null);
        Assertions.assertThatThrownBy(() -> this.moviesRepository.save(userToBeSavedLaunchDataNull))
                .isInstanceOf(ConstraintViolationException.class);

        Movies userToBeSavedImageNull = MoviesCreator.createMoviesToBeSaved();
        userToBeSavedImageNull.setImage(null);
        Assertions.assertThatThrownBy(() -> this.moviesRepository.save(userToBeSavedImageNull))
                .isInstanceOf(ConstraintViolationException.class);

        Movies userToBeSavedDurationNull = MoviesCreator.createMoviesToBeSaved();
        userToBeSavedDurationNull.setDuration(0);
        Assertions.assertThatThrownBy(() -> this.moviesRepository.save(userToBeSavedDurationNull))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Return a movies with id corresponding to the fetched")
    void findById_ReturnMovies_WhenCorrespondingId(){

        Movies savedMovies = moviesRepository.save(MoviesCreator.createMoviesToBeSaved());

        Optional<Movies> findByIdMovies = moviesRepository.findById(savedMovies.getId());

        Assertions.assertThat(findByIdMovies)
                .isNotEmpty()
                .isNotNull();
        Assertions.assertThat(findByIdMovies.get()).usingRecursiveComparison().isEqualTo(savedMovies);
    }

    @Test
    @DisplayName("Returns an empty Optional when ID does not match")
    void findById_ReturnEmptyOptional_WhenDoesNotCorrespondingId(){
        Optional<Movies> findByIdMovies = moviesRepository.findById(0L);

        Assertions.assertThat(findByIdMovies )
                .isEmpty()
                .isNotNull();
    }

    @Test
    @DisplayName("Returns a list of all as movies s stored in the database")
    void findAll_ReturnListMovies_WhenThereAreMovies(){

        Movies moviesToBeSavedOne = MoviesCreator.createMoviesToBeSaved();
        Movies moviesToBeSavedTwo = MoviesCreator.createMoviesToBeSaved();

        Movies savedMoviesOne = moviesRepository.save(moviesToBeSavedOne);
        Movies savedMoviesTwo = moviesRepository.save(moviesToBeSavedTwo);

        List<Movies> listMovies = moviesRepository.findAll();

        Assertions.assertThat(listMovies )
                .isNotNull()
                .isNotEmpty()
                .contains(savedMoviesOne)
                .contains(savedMoviesTwo)
                .hasSize(2);
    }

    @Test
    @DisplayName("Returns an empty list when there are no movies s stored")
    void findAll_ReturnEmptyListMovies_WhenThereAreNoMovies(){
        List<Movies > listMovies = this.moviesRepository.findAll();

        Assertions.assertThat(listMovies )
                .isNotNull()
                .isEmpty();
    }
}