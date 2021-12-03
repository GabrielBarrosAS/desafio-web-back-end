package leadmentoring.desafiowebbackend.mappers;

import leadmentoring.desafiowebbackend.domain.Language;
import leadmentoring.desafiowebbackend.dtos.languageDTOS.LanguagePostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class LanguageMapper {

    public static final LanguageMapper INSTANCE = Mappers.getMapper(LanguageMapper.class);

    public abstract Language toLanguage(LanguagePostDTO languagePostDTO);

}
