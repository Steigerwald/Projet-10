package com.projet7.BibliothequeVille.entity.mapper;


import com.projet7.BibliothequeVille.entity.dto.SearchDTO;
import com.projet7.BibliothequeVille.form.Search;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchMapper extends EntityMapper<SearchDTO, Search>{
}
