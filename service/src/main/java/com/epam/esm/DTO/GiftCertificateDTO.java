package com.epam.esm.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> {
    @NotNull(message = "Name field must not be null")
    private String name;
    private String description;
    @Positive(message = "Price field must be positive")
    private BigDecimal price;
    @Positive(message = "Duration field must be positive")
    private Integer duration;
    private List<TagDTO> tags;

    public GiftCertificateDTO() {
    }

    public GiftCertificateDTO(String name, String description, BigDecimal price, Integer duration, List<TagDTO> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }
}
