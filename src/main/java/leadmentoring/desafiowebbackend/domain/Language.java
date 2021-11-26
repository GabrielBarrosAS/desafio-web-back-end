package leadmentoring.desafiowebbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Language name cannot be empty")
    private String name;
    @NotEmpty(message = "The language tag cannot be empty")
    private String tag;

    @OneToMany(mappedBy = "language")
    private List<Users> usersList;

    @OneToMany(mappedBy = "language")
    private List<Category> categoryList;

    @OneToMany(mappedBy = "language")
    private List<Movies> moviesList;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updateAt = LocalDateTime.now();
}
