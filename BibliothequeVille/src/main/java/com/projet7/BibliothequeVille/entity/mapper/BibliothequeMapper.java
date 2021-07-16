package com.projet7.BibliothequeVille.entity.mapper;

import com.projet7.BibliothequeVille.entity.Bibliotheque;
import com.projet7.BibliothequeVille.entity.dto.BibliothequeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")

public interface BibliothequeMapper{

    BibliothequeMapper INSTANCE= Mappers.getMapper(BibliothequeMapper.class);
    BibliothequeDTO bibliothequeToDTO(Bibliotheque bibliotheque);
    Bibliotheque DTOTOBibliotheque(BibliothequeDTO bibliothequeDTO);

}
