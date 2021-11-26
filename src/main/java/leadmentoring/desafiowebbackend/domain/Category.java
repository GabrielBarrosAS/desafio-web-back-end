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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Category name cannot be empty")
    private String name;

    @ManyToOne
    @JoinColumn(name = "language_id")
    @NotEmpty(message = "Category language cannot be empty")
    private Language language;

    @NotEmpty(message = "The categoty tag cannot be empty")
    private String tag;

    @OneToMany(mappedBy = "category")
    private List<Movies> moviesList;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updateAt = LocalDateTime.now();
}