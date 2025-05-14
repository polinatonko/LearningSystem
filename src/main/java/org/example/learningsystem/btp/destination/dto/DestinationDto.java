package org.example.learningsystem.btp.destination.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DestinationDto {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Type")
    private String type;
    private Map<String, String> properties = new HashMap<>();

    @JsonAnySetter
    public void addProperty(String key, String value) {
        properties.put(key, value);
    }
}
