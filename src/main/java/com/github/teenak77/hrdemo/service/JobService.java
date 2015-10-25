package com.github.teenak77.hrdemo.service;

import com.github.teenak77.hrdemo.model.domain.Job;

import java.util.Collection;

/**
 * Created by teenak on 2015/02/07.
 */
public interface JobService {

    public Collection<Job> getAll();
    public void add(Job job);
    public Job getByKey(Integer key);
    public void updateOne(Job job);
    public void deleteOne(Job job);

}
