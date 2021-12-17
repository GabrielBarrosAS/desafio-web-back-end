package leadmentoring.desafiowebbackend.util.CategoryCreator;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPostDTO;

public class CategoryPostDtoCreator {

    public static Language LANGUAGE_POST_CATEGORY;

    public static CategoryPostDTO createCategoryPostDtoToBeSaved(){
        return CategoryPostDTO.builder()
                .name("Category name")
                .tag("Category tag")
                .languageID(LANGUAGE_POST_CATEGORY.getId())
                .build();
    }
}
