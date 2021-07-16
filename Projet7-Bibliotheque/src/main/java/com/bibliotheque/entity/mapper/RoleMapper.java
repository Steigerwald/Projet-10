package com.bibliotheque.entity.mapper;


import com.bibliotheque.entity.Role;
import com.bibliotheque.entity.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
}
