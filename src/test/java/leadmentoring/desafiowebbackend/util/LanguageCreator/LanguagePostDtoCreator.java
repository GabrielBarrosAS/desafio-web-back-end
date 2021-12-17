package leadmentoring.desafiowebbackend.util.LanguageCreator;

import leadmentoring.desafiowebbackend.dtos.languageDTOS.LanguagePostDTO;

public class LanguagePostDtoCreator {
    public static LanguagePostDTO createLanguagePostDtoToBeSaved(){
        return LanguagePostDTO.builder()
                .name("language")
                .tag("language tag")
                .build();
    }
}
