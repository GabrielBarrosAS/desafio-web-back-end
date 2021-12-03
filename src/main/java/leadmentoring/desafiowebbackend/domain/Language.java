package leadmentoring.desafiowebbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "language",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Users> usersList;

    @OneToMany(mappedBy = "language",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Category> categoryList;

    @OneToMany(mappedBy = "language",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Movies> moviesList;
}
