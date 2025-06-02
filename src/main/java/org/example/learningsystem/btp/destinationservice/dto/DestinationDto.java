package org.example.learningsystem.btp.destinationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXISTING_PROPERTY;

/**
 * Represents an instance of the destination with basic information.
 */
@JsonTypeInfo(use = NAME, include = EXISTING_PROPERTY, property = "Type", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = MailDestinationDto.class, name = "MAIL")})
@Getter
@Setter
public class DestinationDto {

    /**
     * Name of the destination.
     */
    @JsonProperty("Name")
    private String name;

    /**
     * Type of the destination.
     */
    @JsonProperty("Type")
    private String type;
}
