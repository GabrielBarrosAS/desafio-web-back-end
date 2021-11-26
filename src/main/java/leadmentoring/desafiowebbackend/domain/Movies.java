package leadmentoring.desafiowebbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Movie title cannot be empty")
    private String tittle;

    @NotEmpty(message = "Movie synopsis cannot be empty")
    private String synopsis;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotEmpty(message = "Movie category cannot be empty")
    private Category category;

    @NotEmpty(message = "Movie image cannot be empty")
    private String image;

    @NotEmpty(message = "Movie launch data cannot be empty")
    private String launchData;

    @NotEmpty(message = "Movie duration cannot be empty")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "language_id")
    @NotEmpty(message = "Movie language cannot be empty")
    private Language language;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updateAt = LocalDateTime.now();
}
