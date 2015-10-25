package com.github.teenak77.hrdemo.repository.support;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.io.Serializable;

/**
 * Created by teenak on 2015/02/18.
 */
public class CustomJpaRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    /**
     * Creates a new {@link SimpleJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager must not be {@literal null}.
     */
    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    /**
     * Creates a new {@link SimpleJpaRepository} to manage objects of the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     * @param em must not be {@literal null}.
     */
    public CustomJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getMetadata(domainClass, em), em);
    }



    @Transactional
    @Override
    public <S extends T> S save(S entity) {


        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {

            Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
            Object inDatabase = em.find(entity.getClass(), id, LockModeType.PESSIMISTIC_WRITE);
            if(inDatabase == null) {
                throw new ObjectOptimisticLockingFailureException(entity.getClass(), id);
            }

            return em.merge(entity);

        }
    }

}
