package com.epam.esm.service.tag;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.BaseService;

import java.util.List;

public interface TagService extends BaseService<Tag, TagDTO> {

    List<Tag> getAll(int limit, int offset);

    Tag getMostUsedTag();

}
