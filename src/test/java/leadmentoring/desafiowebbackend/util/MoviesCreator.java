package leadmentoring.desafiowebbackend.util;

import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.domain.Users;

import java.time.LocalDateTime;

public class MoviesCreator {
    public static Movies createMoviesToBeSaved(){
        return Movies.builder()
                .tittle("the synopsis")
                .synopsis("the synopsis")
                .launchData("16/06/2000")
                .image("url image movie")
                .duration(120)
                .language(LanguageCreator.createLanguageSaved())
                .category(CategoryCreator.createCategorySaved())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
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
                .language(LanguageCreator.createLanguageSaved())
                .category(CategoryCreator.createCategorySaved())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
}
