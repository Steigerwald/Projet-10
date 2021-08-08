package com.bibliotheque.entity.mapper;

import com.bibliotheque.entity.AttenteReservation;
import com.bibliotheque.entity.dto.AttenteReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AttenteReservationMapper extends EntityMapper<AttenteReservationDTO, AttenteReservation>  {

    @Named("fromEntityToDtoWithoutUser")
    @Mapping(target = "user", ignore = true)
    AttenteReservationDTO fromEntityToDtoWithoutUser(AttenteReservation attenteReservation);

}