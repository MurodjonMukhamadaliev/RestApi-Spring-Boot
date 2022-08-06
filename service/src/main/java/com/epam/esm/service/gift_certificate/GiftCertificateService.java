package com.epam.esm.service.gift_certificate;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.GiftUpdatePriceDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.BaseService;

import java.util.List;
import java.util.UUID;

public interface GiftCertificateService extends BaseService<GiftCertificate, GiftCertificateDTO> {

    GiftCertificate updatePrice(GiftUpdatePriceDto dto);

    List<GiftCertificate> getAll(String name, String description, String sort, int limit, int offset);

    List<GiftCertificate> getCertificatesBySeveralTags(List<String> tags, int limit, int offset);

}
