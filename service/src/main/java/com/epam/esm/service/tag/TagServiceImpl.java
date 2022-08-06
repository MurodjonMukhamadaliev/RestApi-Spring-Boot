package com.epam.esm.service.tag;

import com.epam.esm.DAO.tag.TagDAO;
import com.epam.esm.DAO.tag.TagDAOImpl;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.AlreadyExistsException;
import com.epam.esm.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class TagServiceImpl implements TagService {

    private final ModelMapper modelMapper;
    private final TagDAO tagDAO;

    public TagServiceImpl(ModelMapper modelMapper, TagDAOImpl tagDAO) {
        this.modelMapper = modelMapper;
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional
    public Tag create(TagDTO tagDTO) {
        if (tagDAO.getByName(tagDTO.getName()) != null) {
            throw new AlreadyExistsException("Tag already exists: name = " + tagDTO.getName());
        }
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        return tagDAO.create(tag);
    }

    @Override
    @Transactional
    public Tag update(UUID id, TagDTO tagDTO) {
        Tag tag = tagDAO.get(id);
        if (tag == null) {
            throw new NotFoundException("Tag not found id = " + id);
        }
        if (tagDTO.getName() != null && tagDAO.getByNameAndIdNotEquals(tagDTO.getName(), id) != null) {
            throw new AlreadyExistsException("Tag already exists: name = " + tagDTO.getName());
        }
        modelMapper.map(tagDTO, tag);
        return tagDAO.update(tag);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Tag tag = tagDAO.get(id);
        if (tag == null) {
            throw new NotFoundException("Tag not found id = " + id);
        }
        tagDAO.delete(tag);
    }

    @Override
    public Tag get(UUID id) {
        Tag tag = tagDAO.get(id);
        if (tag == null) {
            throw new NotFoundException("Tag not found id = " + id);
        }
        return tag;
    }

    @Override
    public List<Tag> getAll(int limit, int offset) {
        return tagDAO.getAll(limit, offset);
    }

    @Override
    public Tag getMostUsedTag() {
        return tagDAO.getMostUsedTag();
    }
}
