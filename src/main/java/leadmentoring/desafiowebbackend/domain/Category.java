package leadmentoring.desafiowebbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Category name cannot be empty")
    private String name;

    @NotEmpty(message = "The categoty tag cannot be empty")
    private String tag;

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
    @NotNull(message = "Category language cannot be null")
    private Language language;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Movies> moviesList;
}