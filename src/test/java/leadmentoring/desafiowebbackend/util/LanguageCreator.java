package leadmentoring.desafiowebbackend.util;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Movies;
import leadmentoring.desafiowebbackend.domain.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LanguageCreator {

    public static Language createLanguageToBeSaved(){
        return Language.builder()
                .name("language")
                .tag("language tag")
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .moviesList(new ArrayList<Movies>())
                .categoryList(new ArrayList<Category>())
                .usersList(new ArrayList<Users>())
                .build();
    }

    public static Language createLanguageSaved(){
        return Language.builder()
                .id(1)
                .name("language")
                .tag("language tag")
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .moviesList(new ArrayList<Movies>())
                .categoryList(new ArrayList<Category>())
                .usersList(new ArrayList<Users>())
                .build();
    }

}
