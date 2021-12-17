package leadmentoring.desafiowebbackend.util.MoviesCreator;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPostDTO;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPutDTO;

public class MoviesPutDtoCreator {

    public static Language LANGUAGE_PUT_MOVIES;
    public static Category CATEGORY_PUT_MOVIES;

    public static MoviesPutDTO createMoviesPutDtoToBeUpdate(){
        return MoviesPutDTO.builder()
                .tittle("the synopsis update")
                .synopsis("the synopsis update")
                .launchData("16/06/2000 update")
                .image("url image movie update")
                .duration(240)
                .languageID(LANGUAGE_PUT_MOVIES.getId())
                .categoryID(CATEGORY_PUT_MOVIES.getId())
                .build();
    }

}
