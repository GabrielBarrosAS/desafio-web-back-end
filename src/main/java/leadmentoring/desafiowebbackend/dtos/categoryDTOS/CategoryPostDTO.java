package leadmentoring.desafiowebbackend.dtos.categoryDTOS;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CategoryPostDTO {

    @NotEmpty(message = "Category name cannot be empty")
    @Schema(example = "category")
    private String name;

    @NotEmpty(message = "The categoty tag cannot be empty")
    @Schema(example = "tag-1")
    private String tag;

    @Min(value = 1, message = "Invalid language id, value must be greater than or equal to 1")
    @Schema(example = "1")
    private long languageID;

}
