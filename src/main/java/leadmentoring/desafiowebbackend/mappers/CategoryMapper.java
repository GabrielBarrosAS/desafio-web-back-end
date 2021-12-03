package leadmentoring.desafiowebbackend.mappers;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPostDTO;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract Category toCategory(CategoryPostDTO categoryPostDTO);

    public abstract Category toCategory(CategoryPutDTO categoryPutDTO);
}
