package com.epam.esm.service.gift_certificate;

import com.epam.esm.DAO.gift_certificate.GiftCertificateDAO;
import com.epam.esm.DAO.gift_certificate.GiftCertificateDAOImpl;
import com.epam.esm.DAO.tag.TagDAOImpl;
import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.GiftUpdatePriceDto;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.AlreadyExistsException;
import com.epam.esm.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final ModelMapper modelMapper;
    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAOImpl tagDAOImpl;

    public GiftCertificateServiceImpl(ModelMapper modelMapper, GiftCertificateDAOImpl giftCertificateDAO, TagDAOImpl tagDAOImpl) {
        this.modelMapper = modelMapper;
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAOImpl = tagDAOImpl;
    }

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificateDTO giftCertificateDTO) {
        if (giftCertificateDAO.getByNameAndIdNotEquals(giftCertificateDTO.getName(), null) != null) {
            throw new AlreadyExistsException("Gift Certificate already exists: name = " + giftCertificateDTO.getName());
        }
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDTO, GiftCertificate.class);
        if (giftCertificateDTO.getTags() != null) {
            giftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDTO.getTags()));
        }
        return giftCertificateDAO.create(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificate update(UUID id, GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = giftCertificateDAO.get(id);
        if (giftCertificate == null || giftCertificate.getId() == null) {
            throw new NotFoundException("Gift Certificate not found id = " + id);
        }
        if (giftCertificateDAO.getByNameAndIdNotEquals(giftCertificateDTO.getName(), id) != null) {
            throw new AlreadyExistsException("Gift Certificate already exists: name = " + giftCertificateDTO.getName());
        }
        modelMapper.map(giftCertificateDTO, giftCertificate);
        if (giftCertificateDTO.getTags() != null) {
            giftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDTO.getTags()));
        }
        return giftCertificateDAO.update(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificate updatePrice(GiftUpdatePriceDto dto) {
        GiftCertificate giftCertificate = giftCertificateDAO.get(dto.getId());
        if (giftCertificate == null || giftCertificate.getId() == null) {
            throw new NotFoundException("Gift Certificate not found id = " + dto.getId());
        }
        giftCertificate.setPrice(dto.getPrice());
        return giftCertificateDAO.update(giftCertificate);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        GiftCertificate giftCertificate = giftCertificateDAO.get(id);
        if (giftCertificate == null) {
            throw new NotFoundException("Gift Certificate not found id = " + id);
        }
        giftCertificateDAO.delete(giftCertificate);
    }

    @Override
    public GiftCertificate get(UUID id) {
        GiftCertificate giftCertificate = giftCertificateDAO.get(id);
        if (giftCertificate == null) {
            throw new NotFoundException("Gift Certificate not found id = " + id);
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> getAll(String name, String description, String sort, int limit, int offset) {
        return giftCertificateDAO.getAll(name, description, sort, limit, offset);
    }

    @Override
    public List<GiftCertificate> getCertificatesBySeveralTags(List<String> tags, int limit, int offset) {
        List<Tag> tagList = new ArrayList<>();
        tags.forEach(tag -> {
            Tag tagByName = tagDAOImpl.getByName(tag);
            if (tagByName == null) {
                throw new NotFoundException("Tag not found name = " + tag);
            }
            tagList.add(tagByName);
        });
        return giftCertificateDAO.getCertificatesBySeveralTags(tagList, limit, offset);
    }

    private List<Tag> setTagsToGiftCertificate(List<TagDTO> tagDTOList) {
        List<Tag> tags = new ArrayList<>();
        tagDTOList.forEach(tagDTO -> {
            Tag tag = tagDAOImpl.getByName(tagDTO.getName());
            if (tag != null) {
                tags.add(tag);
            } else {
                Tag newTag = modelMapper.map(tagDTO, Tag.class);
                tags.add(tagDAOImpl.create(newTag));
            }
        });
        return tags;
    }
}