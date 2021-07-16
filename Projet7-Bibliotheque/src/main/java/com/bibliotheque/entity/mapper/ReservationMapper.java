package com.bibliotheque.entity.mapper;

import com.bibliotheque.entity.Reservation;
import com.bibliotheque.entity.dto.ReservationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation>{
}
