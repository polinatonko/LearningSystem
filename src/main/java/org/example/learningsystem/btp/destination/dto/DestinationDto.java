package org.example.learningsystem.btp.destination.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = Id.NAME, include = As.EXISTING_PROPERTY, property = "Type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = MailDestinationDto.class, name = "MAIL")})
@Getter
@Setter
public class DestinationDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Type")
    private String type;
}
