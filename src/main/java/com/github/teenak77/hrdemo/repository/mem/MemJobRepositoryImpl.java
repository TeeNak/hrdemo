package com.github.teenak77.hrdemo.repository.mem;

import com.github.teenak77.hrdemo.model.domain.Job;
import com.github.teenak77.hrdemo.repository.JobRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.TreeMap;

/**
 * Created by teenak on 2015/02/07.
 */
@Repository
public class MemJobRepositoryImpl implements JobRepository {

    TreeMap<Integer, Job> jobList = new TreeMap<Integer, Job>();

    public int findNewKey() {

        if( jobList.size() <= 0) {
            return 1;
        }
        return jobList.lastKey() + 1;

    }

    @Override
    public Collection<Job> findAll() {
        return jobList.values();
    }


    @Override
    public Job findOne(Integer key) {
        return jobList.get(key);
    }

    @Override
    public void save(Job job) {

        if(job.getKey() == null || job.getKey() == 0) {
            job.setKey(findNewKey());
        }

        jobList.put(job.getKey(), job);
    }

    @Override
    public void delete(Job job) {
        jobList.remove(job.getKey());
    }
}
