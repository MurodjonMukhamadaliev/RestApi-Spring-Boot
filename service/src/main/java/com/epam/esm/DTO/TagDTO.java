package com.epam.esm.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO extends RepresentationModel<TagDTO> {
    @NotNull(message = "Name field must not be null")
    private String name;

    public TagDTO() {
    }

    public TagDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
