package com.bibliotheque.entity.mapper;

import com.bibliotheque.entity.dto.SearchDTO;
import com.bibliotheque.form.Search;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchMapper extends EntityMapper<SearchDTO, Search>{
}
