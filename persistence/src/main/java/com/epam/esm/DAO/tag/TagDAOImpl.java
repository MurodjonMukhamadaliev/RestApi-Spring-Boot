package com.epam.esm.DAO.tag;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;


@Repository
public class TagDAOImpl implements TagDAO {

    private final EntityManager entityManager;

    private static final String QUERY_GET_ALL_TAG = "select t from Tag t";

    private static final String GET_MOST_USED_TAG = "select * from tag where id in (select tag_id from gift_certificate_tag where gift_certificate_id in " +
            "(select giftcertificate_id from orders where user_id = (select user_id from orders group by user_id order by sum(cost) desc limit 1)) " +
            "group by tag_id order by count(tag_id) desc limit 1);";

    public TagDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Tag update(Tag tag) {
        return entityManager.merge(tag);
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Tag get(UUID id) {
        try {
            return entityManager.find(Tag.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Tag> getAll(int limit, int offset) {
        return entityManager
                .createQuery(QUERY_GET_ALL_TAG, Tag.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Tag getByName(String name) {
        String QUERY_GET_TAG_BY_NAME = "select t from Tag t where t.name=:name";
        try {
            return entityManager
                    .createQuery(QUERY_GET_TAG_BY_NAME, Tag.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Tag getByNameAndIdNotEquals(String name, UUID id) {
        String QUERY_GET_TAG_BY_NAME_AND_ID_NOT_EQUAL = "select * from tag where name='" + name + "'";
        if (id != null) {
            QUERY_GET_TAG_BY_NAME_AND_ID_NOT_EQUAL += " and id!='" + id + "'";
        }
        try {
            return (Tag) entityManager
                    .createNativeQuery(QUERY_GET_TAG_BY_NAME_AND_ID_NOT_EQUAL, Tag.class)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Tag getMostUsedTag() {
        return (Tag) entityManager
                .createNativeQuery(GET_MOST_USED_TAG, Tag.class)
                .getSingleResult();
    }
}
