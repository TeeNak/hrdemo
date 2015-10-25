package com.github.teenak77.hrdemo.repository.springdatajpa;

import com.github.teenak77.hrdemo.model.domain.Job;
import com.github.teenak77.hrdemo.repository.JobRepository;
import org.springframework.data.repository.Repository;

/**
 * Created by teenak on 2015/02/11.
 */
public interface SpringDataJobRepository extends JobRepository, Repository<Job, Integer> {
}
