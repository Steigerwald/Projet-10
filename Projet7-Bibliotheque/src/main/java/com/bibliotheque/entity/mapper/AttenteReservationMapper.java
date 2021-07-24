package com.bibliotheque.entity.mapper;

import com.bibliotheque.entity.AttenteReservation;
import com.bibliotheque.entity.dto.AttenteReservationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttenteReservationMapper extends EntityMapper<AttenteReservationDTO, AttenteReservation>  {
}