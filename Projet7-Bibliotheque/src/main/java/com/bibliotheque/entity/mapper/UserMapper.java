package com.bibliotheque.entity.mapper;

import com.bibliotheque.entity.User;
import com.bibliotheque.entity.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User>{
}
