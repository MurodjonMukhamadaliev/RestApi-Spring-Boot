package com.epam.esm.service.utils;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GiftCertificateUtils {

    public static UUID uuid() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public static GiftCertificateDTO giftCertificateDTO() {
        return new GiftCertificateDTO(
                "Gift",
                "Description",
                1.1,
                1,
                List.of(new TagDTO("Tag"))
        );
    }

    public static GiftCertificateDTO updatedGiftCertificateDTO() {
        return new GiftCertificateDTO(
                "New Gift",
                "Description",
                1.1,
                1,
                List.of(new TagDTO("Tag"))
        );
    }

    public static GiftCertificate giftCertificate() {
        return new GiftCertificate(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Gift",
                "Description",
                1.1,
                1,
                List.of(new Tag("Tag"))
        );
    }

    public static GiftCertificate updatedGiftCertificate() {
        return new GiftCertificate(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "New Gift",
                "Description",
                1.1,
                1,
                List.of(new Tag("Tag"))
        );
    }

    public static GiftCertificate updatedPriceOfGiftCertificate() {
        return new GiftCertificate(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "New Gift",
                "Description",
                1.2,
                1,
                List.of(new Tag("Tag"))
        );
    }

}
