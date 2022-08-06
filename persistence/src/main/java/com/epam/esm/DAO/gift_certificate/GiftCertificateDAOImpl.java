package com.epam.esm.DAO.gift_certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;


@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private final EntityManager entityManager;

    private final static String GET_CERTIFICATES_BY_SEVERAL_TAGS = "select gc from GiftCertificate gc left join gc.tags t group by gc " +
            "having sum (case when t in (:tags) then 1 else 0 end) = :tagsSize";

    public GiftCertificateDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }

    @Override
    public GiftCertificate get(UUID id) {
        try {
            return entityManager.find(GiftCertificate.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GiftCertificate> getAll(String name, String description, String sortParams, int limit, int offset) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(root);

        createCriteriaQuery(criteriaQuery, criteriaBuilder, root, name, description, sortParams);

        return entityManager
                .createQuery(criteriaQuery)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<GiftCertificate> getCertificatesBySeveralTags(List<Tag> tags, int limit, int offset) {
        return entityManager
                .createQuery(GET_CERTIFICATES_BY_SEVERAL_TAGS, GiftCertificate.class)
                .setParameter("tags", tags)
                .setParameter("tagsSize", (long) tags.size())
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public GiftCertificate getByNameAndIdNotEquals(String name, UUID id) {
        String QUERY_GET_CERTIFICATE_BY_NAME_AND_ID_NOT_EQUAL = "select * from gift_certificate where name='" + name + "'";
        if (id != null) {
            QUERY_GET_CERTIFICATE_BY_NAME_AND_ID_NOT_EQUAL += " and id!='" + id + "'";
        }
        try {
            return (GiftCertificate) entityManager
                    .createNativeQuery(QUERY_GET_CERTIFICATE_BY_NAME_AND_ID_NOT_EQUAL, GiftCertificate.class)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    private void createCriteriaQuery(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root, String name, String description, String sortParams) {
        if (name != null) {
            criteriaQuery.where(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        } else if (description != null) {
            criteriaQuery.where(criteriaBuilder.like(root.get("description"), "%" + description + "%"));
        }

        if (sortParams != null) {
            if (sortParams.contains("name_desc")) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
            } else if (sortParams.contains("name") || sortParams.contains("name_asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
            }

            if (sortParams.contains("create_date_desc")) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
            } else if (sortParams.contains("create_date") || sortParams.contains("create_date_asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createDate")));
            }

            if (sortParams.contains("last_update_date_desc")) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("lastUpdateDate")));
            } else if (sortParams.contains("last_update_date") || sortParams.contains("last_update_date_asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("lastUpdateDate")));
            }
        }
    }

}