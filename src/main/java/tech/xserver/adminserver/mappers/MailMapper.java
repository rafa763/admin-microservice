package tech.xserver.adminserver.mappers;

import org.mapstruct.Mapper;
import tech.xserver.adminserver.DTO.MailDto;
import tech.xserver.adminserver.model.Mail;

@Mapper(componentModel = "spring")
public interface MailMapper {
    MailDto mapTo(Mail mail);
    Mail mapFrom(MailDto mailDto);
}
