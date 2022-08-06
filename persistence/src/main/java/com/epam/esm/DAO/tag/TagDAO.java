package com.epam.esm.DAO.tag;

import com.epam.esm.DAO.BaseDAO;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface TagDAO extends BaseDAO<Tag> {

    Tag getByName(String name);

    Tag getByNameAndIdNotEquals(String name, UUID id);

    List<Tag> getAll(int limit, int offset);

    Tag getMostUsedTag();

}
