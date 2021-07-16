package com.projet7.BibliothequeVille.entity.mapper;

import com.projet7.BibliothequeVille.entity.Reservation;
import com.projet7.BibliothequeVille.entity.dto.ReservationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation>{
}
