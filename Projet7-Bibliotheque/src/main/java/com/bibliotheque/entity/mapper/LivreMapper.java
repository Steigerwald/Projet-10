package com.bibliotheque.entity.mapper;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.dto.LivreDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivreMapper extends EntityMapper<LivreDTO, Livre> {


}
