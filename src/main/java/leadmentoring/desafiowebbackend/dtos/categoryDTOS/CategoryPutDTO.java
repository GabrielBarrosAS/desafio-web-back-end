package leadmentoring.desafiowebbackend.dtos;

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
public class CategoryPutDTO {

    @Min(value = 1, message = "Invalid id, value must be greater than or equal to 1")
    private long id;

    @NotEmpty(message = "Category name cannot be empty")
    private String name;

    @NotEmpty(message = "The categoty tag cannot be empty")
    private String tag;

    @Valid
    @NotNull(message = "Category language cannot be null")
    private Language language;

    @NotNull(message = "Active cannot be null")
    private Boolean active;
}
