package com.github.teenak77.hrdemo.repository.jpa;

import com.github.teenak77.hrdemo.model.domain.Job;
import com.github.teenak77.hrdemo.repository.JobRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Created by teenak on 2015/02/08.
 */
@Repository
public class JpaJobRepositoryImpl implements JobRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Collection<Job> findAll() {
        List rs = this.em.createQuery("SELECT job FROM Job job ORDER BY job.key").getResultList();
        return rs;
    }

    @Override
    public Job findOne(Integer key) {
        return this.em.find(Job.class, key);
    }

    @Override
    public void save(Job job) {
        if (job.getKey() == null || job.getKey() == 0) {
            this.em.persist(job);
        }
        else {
            this.em.merge(job);
        }

    }

    @Override
    public void delete(Job job) {

        // Even if the record is already deleted, the user wanted to delete this record anyway
        // so there is no need of optimistic locking error.
        em.remove(em.contains(job) ? job : em.merge(job));
    }
}
