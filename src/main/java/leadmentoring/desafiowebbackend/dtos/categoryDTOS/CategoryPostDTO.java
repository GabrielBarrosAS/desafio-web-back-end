package leadmentoring.desafiowebbackend.dtos.categoryDTOS;

import leadmentoring.desafiowebbackend.domain.Language;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPostDTO {

    @NotEmpty(message = "Category name cannot be empty")
    private String name;

    @NotEmpty(message = "The categoty tag cannot be empty")
    private String tag;

    @Valid
    @NotNull(message = "Category language cannot be null")
    private Language language;

}
