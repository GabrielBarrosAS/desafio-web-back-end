package leadmentoring.desafiowebbackend.mappers;

import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPostDTO;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class MoviesMapper {

    public static final MoviesMapper INSTANCE = Mappers.getMapper(MoviesMapper.class);

    public abstract Movies toMovies(MoviesPostDTO moviesPostDTO);

    public abstract Movies toMovies(MoviesPutDTO moviesPutDTO);

}
