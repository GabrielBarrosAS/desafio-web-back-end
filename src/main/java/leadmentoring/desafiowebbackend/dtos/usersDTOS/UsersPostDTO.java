package leadmentoring.desafiowebbackend.dtos.usersDTOS;

import io.swagger.v3.oas.annotations.media.Schema;
import leadmentoring.desafiowebbackend.domain.Language;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersPostDTO {

    @NotEmpty(message = "Username cannot be empty")
    @Schema(example = "Username")
    private String name;

    @CPF(message = "Please, provide a valid cpf")
    @Schema(description = "https://www.4devs.com.br/gerador_de_cpf",example = "32549970004")
    private String cpf;

    @NotEmpty(message = "Telephone cannot be empty")
    @Pattern(regexp = "^[0-9]{9}")
    @Schema(example = "888888888")
    private String telephone;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    @Schema(example = "emailuser@emailuser.com")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 5,max = 512,message = "Password must be between 5 and 512 alphanumeric characters")
    @Schema(example = "password")
    private String password;

    @NotEmpty(message = "Profile cannot be empty")
    @Schema(example = "url image profile")
    private String profile;

    @Min(value = 1, message = "Invalid language id, value must be greater than or equal to 1")
    @Schema(example = "1")
    private long languageID;

    @NotEmpty(message = "Roles cannot be empty")
    @Schema(example = "ROLE_USER")
    private String roles;

}
