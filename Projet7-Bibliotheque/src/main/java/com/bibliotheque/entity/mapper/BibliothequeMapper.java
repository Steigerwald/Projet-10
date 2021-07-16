package com.bibliotheque.entity.mapper;

import com.bibliotheque.entity.Bibliotheque;
import com.bibliotheque.entity.dto.BibliothequeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BibliothequeMapper extends EntityMapper<BibliothequeDTO, Bibliotheque>  {
}
