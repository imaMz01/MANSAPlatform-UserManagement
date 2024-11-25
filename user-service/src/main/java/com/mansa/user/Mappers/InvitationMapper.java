package com.mansa.user.Mappers;

import com.mansa.user.Dtos.InvitationDto;
import com.mansa.user.Entities.Invitation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvitationMapper {

    InvitationDto toDto(Invitation role);
    Invitation toEntity(InvitationDto roleDto);
    List<InvitationDto> toDto(List<Invitation> role);
    List<Invitation> toEntity(List<InvitationDto> roleDto);
}
