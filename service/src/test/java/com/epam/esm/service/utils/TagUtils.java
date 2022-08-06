package com.epam.esm.service.utils;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.entity.Tag;

import java.time.LocalDateTime;
import java.util.UUID;

public class TagUtils {

    public static UUID uuid() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public static TagDTO tagDTO() {
        return new TagDTO("Tag");
    }

    public static TagDTO updatedTagDTO() {
        return new TagDTO("New Tag");
    }

    public static Tag tag() {
        return new Tag(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Tag"
        );
    }

    public static Tag updatedTag() {
        return new Tag(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "New Tag"
        );
    }

}
