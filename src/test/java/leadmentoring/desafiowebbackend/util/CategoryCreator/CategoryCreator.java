package leadmentoring.desafiowebbackend.util.CategoryCreator;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.domain.Movies;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CategoryCreator {
    public static Language LANGUAGE_CATEGORY;

    public static Category createCategoryToBeSaved(){
        return Category.builder()
                .name("Category name")
                .tag("Category tag")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .language(LANGUAGE_CATEGORY)
                .moviesList(new ArrayList<Movies>())
                .build();
    }

    public static Category createCategorySaved(){
        return Category.builder()
                .id(1)
                .name("Category name")
                .tag("Category tag")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .language(LANGUAGE_CATEGORY)
                .moviesList(new ArrayList<Movies>())
                .build();
    }

}
