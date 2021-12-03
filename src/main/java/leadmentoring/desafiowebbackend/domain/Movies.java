package leadmentoring.desafiowebbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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

    @NotEmpty(message = "Movie image cannot be empty")
    private String image;

    @NotEmpty(message = "Movie launch data cannot be empty")
    private String launchData;

    @Min(value = 1, message = "Movie duration must be at least 1 minute")
    private int duration;

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
    @NotNull(message = "Movie language cannot be empty")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Valid
    @NotNull(message = "Movie category cannot be null")
    private Category category;
}
