package leadmentoring.desafiowebbackend.util.CategoryCreator;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPostDTO;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPutDTO;

public class CategoryPutDtoCreator {

    public static Language LANGUAGE_PUT_CATEGORY;

    public static CategoryPutDTO createCategoryPutDtoToBeUpdate(){
        return CategoryPutDTO.builder()
                .id(1)
                .name("Category name update")
                .tag("Category tag update")
                .active(true)
                .languageID(LANGUAGE_PUT_CATEGORY.getId())
                .build();
    }
}
