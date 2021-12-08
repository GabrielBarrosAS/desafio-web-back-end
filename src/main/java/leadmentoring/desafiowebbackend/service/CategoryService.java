package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPostDTO;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPutDTO;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundException;
import leadmentoring.desafiowebbackend.mappers.CategoryMapper;
import leadmentoring.desafiowebbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final LanguageService languageService;

    public List<Category> listAllNoPageable(){

        return categoryRepository.findAll()
                .stream()
                .filter(Category::getActive)
                .collect(Collectors.toList());
    }

    public Category findById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public Category save(CategoryPostDTO categoryPostDTO){

        Category category = CategoryMapper.INSTANCE.toCategory(categoryPostDTO);

        Language language = languageService.findById(category.getLanguage().getId());

        BeanUtils.copyProperties(language, category.getLanguage());

        category.setActive(true);

        return categoryRepository.save(category);
    }

    public Category update(CategoryPutDTO categoryPutDTO){

        Category categoryPut = CategoryMapper.INSTANCE.toCategory(categoryPutDTO);

        Category databaseCategory = findById(categoryPut.getId());

        Language language = languageService.findById(categoryPut.getLanguage().getId());

        BeanUtils.copyProperties(language, categoryPut.getLanguage());

        BeanUtils.copyProperties(categoryPut,databaseCategory, "createdAt");

        return categoryRepository.save(databaseCategory);
    }

    public Category delete(long id){
        Category category = findById(id);

        category.setActive(false);

        categoryRepository.save(category);

        return category;
    }

}
