package leadmentoring.desafiowebbackend.dtos.usersDTOS;

import leadmentoring.desafiowebbackend.domain.Language;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersPutDTO {

    @Min(value = 1, message = "Invalid id, value must be greater than or equal to 1")
    private long id;

    @NotEmpty(message = "Username cannot be empty")
    private String name;

    @CPF(message = "Please, provide a valid cpf")
    private String cpf;

    @NotEmpty(message = "Telephone cannot be empty")
    @Pattern(regexp = "^[0-9]{9}")
    private String telephone;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 5,max = 10,message = "Password must be between 5 and 10 alphanumeric characters")
    private String password;

    @NotEmpty(message = "Profile cannot be empty")
    private String profile;

    @Valid
    @NotNull(message = "Language cannot be null")
    private Language language;

    @NotEmpty(message = "Roles cannot be empty")
    private String roles;

    @NotNull(message = "Active cannot be null")
    private Boolean active;
}
