package leadmentoring.desafiowebbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotEmpty(message = "Roles cannot be empty")
    private String roles;

    @Column(nullable=false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @NotNull(message = "Active cannot be null")
    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "language_id")
    @Valid
    @NotNull(message = "Language cannot be null")
    private Language language;
}
