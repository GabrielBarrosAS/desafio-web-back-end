package leadmentoring.desafiowebbackend.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguagePostDTO {

    @NotEmpty(message = "Language name cannot be empty")
    private String name;
    @NotEmpty(message = "The language tag cannot be empty")
    private String tag;

}
