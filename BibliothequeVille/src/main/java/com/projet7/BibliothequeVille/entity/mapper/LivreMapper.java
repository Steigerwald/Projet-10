package com.projet7.BibliothequeVille.entity.mapper;

import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.entity.dto.LivreDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivreMapper extends EntityMapper<LivreDTO, Livre> {


}
