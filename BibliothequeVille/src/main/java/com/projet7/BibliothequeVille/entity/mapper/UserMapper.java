package com.projet7.BibliothequeVille.entity.mapper;

import com.projet7.BibliothequeVille.entity.User;
import com.projet7.BibliothequeVille.entity.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User>{
}
