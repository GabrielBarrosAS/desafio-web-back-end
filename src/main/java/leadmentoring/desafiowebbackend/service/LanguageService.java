package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.LanguageDTO;
import leadmentoring.desafiowebbackend.exception.BadRequestException;
import leadmentoring.desafiowebbackend.mappers.LanguageMapper;
import leadmentoring.desafiowebbackend.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public List<Language> listAllNoPageable(){
        return languageRepository.findAll();
    }

    public Language findById(Long id){
        return languageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Language not found"));
    }

    public Language save(LanguageDTO languageDTO){

        Language language = languageRepository.save(LanguageMapper.INSTANCE.toLanguage(languageDTO));

        return language;
    }

}
