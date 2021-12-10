package leadmentoring.desafiowebbackend.util;

import leadmentoring.desafiowebbackend.domain.Language;

public class LanguageCreator {

    public static Language createLanguageToBeSaved(){
        return Language.builder()
                .name("language")
                .tag("language tag")
                .build();
    }

    public static Language createLanguageSaved(){
        return Language.builder()
                .id(1)
                .name("language")
                .tag("language tag")
                .build();
    }

}
