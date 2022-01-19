package leadmentoring.desafiowebbackend.dtos.usersDTOS;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {


    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    @Schema(example = "emailuser@emailuser.com")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 5,max = 512,message = "Password must be between 5 and 512 alphanumeric characters")
    @Schema(example = "password")
    private String password;

}
