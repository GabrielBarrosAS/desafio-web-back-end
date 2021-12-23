package leadmentoring.desafiowebbackend.dtos.languageDTOS;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguagePostDTO {

    @NotEmpty(message = "Language name cannot be empty")
    @Schema(example = "portugues")
    private String name;
    @NotEmpty(message = "The language tag cannot be empty")
    @Schema(example = "pt-br")
    private String tag;

}
