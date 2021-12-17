package leadmentoring.desafiowebbackend.util.MoviesCreator;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.domain.Users;

import java.time.LocalDateTime;

public class MoviesCreator {

    public static Language LANGUAGE_MOVIES;
    public static Category CATEGORY_MOVIES;

    public static Movies createMoviesToBeSaved(){
        return Movies.builder()
                .tittle("the synopsis")
                .synopsis("the synopsis")
                .launchData("16/06/2000")
                .image("url image movie")
                .duration(120)
                .active(true)
                .language(LANGUAGE_MOVIES)
                .category(CATEGORY_MOVIES)
                .build();
    }

    public static Movies createMoviesSaved() {
        return Movies.builder()
                .id(1)
                .tittle("the synopsis")
                .synopsis("the synopsis")
                .launchData("16/06/2000")
                .image("url image movie")
                .duration(120)
                .language(LANGUAGE_MOVIES)
                .category(CATEGORY_MOVIES)
                .active(true)
                .build();
    }

    public static Movies createMoviesUpdated() {
        return Movies.builder()
                .id(1)
                .tittle("the synopsis update")
                .synopsis("the synopsis update")
                .launchData("16/06/2000 update")
                .image("url image movie update")
                .duration(240)
                .language(LANGUAGE_MOVIES)
                .category(CATEGORY_MOVIES)
                .active(true)
                .build();
    }

    public static Movies createMoviesDeleted() {
        return Movies.builder()
                .id(1)
                .tittle("the synopsis")
                .synopsis("the synopsis")
                .launchData("16/06/2000")
                .image("url image movie")
                .duration(120)
                .language(LANGUAGE_MOVIES)
                .category(CATEGORY_MOVIES)
                .active(false)
                .build();
    }
}
