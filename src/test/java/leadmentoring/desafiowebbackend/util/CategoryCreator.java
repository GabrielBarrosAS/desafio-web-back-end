package leadmentoring.desafiowebbackend.util;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Movies;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CategoryCreator {
    public static Category createCategoryToBeSaved(Language language){
        return Category.builder()
                .name("Category name")
                .tag("Category tag")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .language(language)
                .moviesList(new ArrayList<Movies>())
                .build();
    }

    public static Category createCategorySaved(Language language){
        return Category.builder()
                .id(language.getId())//ideia de pegar o mesmo id correspondente da linguagem
                .name("Category name")
                .tag("Category tag")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .language(language)
                .moviesList(new ArrayList<Movies>())
                .build();
    }

}
