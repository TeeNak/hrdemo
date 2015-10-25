package com.github.teenak77.hrdemo.repository.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

/**
 * Created by teenak on 2015/02/19.
 */
public class CustomRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {

    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

        return new CustomRepositoryFactory(entityManager);
    }

    private static class CustomRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        private EntityManager entityManager;

        public CustomRepositoryFactory(EntityManager entityManager) {
            super(entityManager);

            this.entityManager = entityManager;
        }

        protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata,
                                                                                             EntityManager entityManager) {

            Class<?> repositoryInterface = metadata.getRepositoryInterface();
            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());

            SimpleJpaRepository<?, ?> repo = new CustomJpaRepositoryImpl<T, I>((Class<T>) metadata.getDomainType(), entityManager);

            return repo;
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

            // The RepositoryMetadata can be safely ignored, it is used by the JpaRepositoryFactory
            //to check for QueryDslJpaRepository's which is out of scope.
            return CustomJpaRepository.class;
        }

        private boolean isQueryDslExecutor(Class<?> repositoryInterface) {

            return false;
        }


    }
}
