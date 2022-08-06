package com.epam.esm.DAO.gift_certificate;

import com.epam.esm.DAO.BaseDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface GiftCertificateDAO extends BaseDAO<GiftCertificate> {

    List<GiftCertificate> getAll(String name, String description, String sortParams, int limit, int offset);

    List<GiftCertificate> getCertificatesBySeveralTags(List<Tag> tags, int limit, int offset);

    GiftCertificate getByNameAndIdNotEquals(String name, UUID id);

}
