package leadmentoring.desafiowebbackend.dtos.moviesDTOS;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoviesPutDTO {

    @Min(value = 1, message = "Invalid id, value must be greater than or equal to 1")
    private long id;

    @NotEmpty(message = "Movie title cannot be empty")
    private String tittle;

    @NotEmpty(message = "Movie synopsis cannot be empty")
    private String synopsis;

    @Min(value = 1, message = "Invalid category id, value must be greater than or equal to 1")
    private long categoryID;

    @NotEmpty(message = "Movie image cannot be empty")
    private String image;

    @NotEmpty(message = "Movie launch data cannot be empty")
    private String launchData;

    @Min(value = 1, message = "Movie duration must be at least 1 minute")
    private int duration;

    @Min(value = 1, message = "Invalid language id, value must be greater than or equal to 1")
    private long languageID;

    @NotNull(message = "Active cannot be null")
    private Boolean active;

}
