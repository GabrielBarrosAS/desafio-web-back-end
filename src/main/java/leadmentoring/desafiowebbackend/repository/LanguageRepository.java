package leadmentoring.desafiowebbackend.repository;

import leadmentoring.desafiowebbackend.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language,Long> {
}
