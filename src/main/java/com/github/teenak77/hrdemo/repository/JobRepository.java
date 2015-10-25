package com.github.teenak77.hrdemo.repository;

import com.github.teenak77.hrdemo.model.domain.Job;

import java.util.Collection;

/**
 * Created by teenak on 2015/02/07.
 */
public interface JobRepository {

    public Collection<Job> findAll();

    public Job findOne(Integer key);

    public void save(Job job);

    public void delete(Job job);

}
