package leadmentoring.desafiowebbackend.service;

import leadmentoring.desafiowebbackend.domain.Category;
import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPostDTO;
import leadmentoring.desafiowebbackend.dtos.categoryDTOS.CategoryPutDTO;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundException;
import leadmentoring.desafiowebbackend.mappers.CategoryMapper;
import leadmentoring.desafiowebbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

        Language language = languageService.findById(categoryPostDTO.getLanguageID());

        category.setLanguage(language);

        category.setActive(true);

        return categoryRepository.save(category);
    }

    public Category update(CategoryPutDTO categoryPutDTO){

        Category categoryPut = CategoryMapper.INSTANCE.toCategory(categoryPutDTO);

        categoryPut.setCreatedAt(findById(categoryPut.getId()).getCreatedAt());

        Language language = languageService.findById(categoryPutDTO.getLanguageID());

        categoryPut.setLanguage(language);

        return categoryRepository.save(categoryPut);
    }

    public Category delete(long id){
        Category category = findById(id);

        category.setActive(false);

        categoryRepository.save(category);

        return category;
    }

}
