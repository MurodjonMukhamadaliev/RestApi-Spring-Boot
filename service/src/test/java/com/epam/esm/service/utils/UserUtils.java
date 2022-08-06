package com.epam.esm.service.utils;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;


public class UserUtils {

    public static UUID uuid() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public static User user() {
        return new User(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Username",
                "+99800000000",
                null
        );
    }

    public static UserDTO userDTO() {
        return new UserDTO(
                "Username",
                "+99800000000"
        );
    }

}
