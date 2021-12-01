package leadmentoring.desafiowebbackend.repository;

import leadmentoring.desafiowebbackend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
