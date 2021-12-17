package leadmentoring.desafiowebbackend.util.MoviesCreator;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPostDTO;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPostDTO;

public class MoviesPostDtoCreator {

    public static Language LANGUAGE_POST_MOVIES;
    public static Category CATEGORY_POST_MOVIES;

    public static MoviesPostDTO createMoviesPostDtoToBeSaved(){
        return MoviesPostDTO.builder()
                .tittle("the synopsis")
                .synopsis("the synopsis")
                .launchData("16/06/2000")
                .image("url image movie")
                .duration(120)
                .languageID(LANGUAGE_POST_MOVIES.getId())
                .categoryID(CATEGORY_POST_MOVIES.getId())
                .build();
    }

}
