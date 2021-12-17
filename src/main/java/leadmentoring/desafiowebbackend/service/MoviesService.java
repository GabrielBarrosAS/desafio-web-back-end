package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPostDTO;
import leadmentoring.desafiowebbackend.dtos.moviesDTOS.MoviesPutDTO;
import leadmentoring.desafiowebbackend.exception.badRequest.BadRequestException;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundException;
import leadmentoring.desafiowebbackend.mappers.MoviesMapper;
import leadmentoring.desafiowebbackend.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MoviesService {
    
    private final MoviesRepository moviesRepository;
    private final CategoryService categoryService;
    private final LanguageService languageService;

    public List<Movies> listAllNoPageable(){

        return moviesRepository.findAll()
                .stream()
                .filter(Movies::getActive)
                .collect(Collectors.toList());
    }

    public Movies findById(Long id){
        return moviesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movies not found"));
    }

    public Movies save(MoviesPostDTO moviesPostDTO){

        Movies movie = MoviesMapper.INSTANCE.toMovies(moviesPostDTO);

        Language languageMovie = languageService.findById(moviesPostDTO.getLanguageID());

        Category category = categoryService.findById(moviesPostDTO.getCategoryID());

        movie.setLanguage(languageMovie);

        movie.setCategory(category);

        movie.setActive(true);

        return moviesRepository.save(movie);
    }

    public Movies update(MoviesPutDTO moviesPutDTO){

        Movies moviePut = MoviesMapper.INSTANCE.toMovies(moviesPutDTO);

        moviePut.setCreatedAt(findById(moviePut.getId()).getCreatedAt());

        Language languageMovie = languageService.findById(moviesPutDTO.getLanguageID());

        Category category = categoryService.findById(moviesPutDTO.getCategoryID());

        moviePut.setLanguage(languageMovie);

        moviePut.setCategory(category);

        return moviesRepository.save(moviePut);
    }

    public Movies delete(long id){
        Movies movie = findById(id);

        movie.setActive(false);

        return moviesRepository.save(movie);
    }
    
}
