package com.github.teenak77.hrdemo.repository.jdbc;

import com.github.teenak77.hrdemo.model.domain.Job;
import com.github.teenak77.hrdemo.exception.AppException;
import com.github.teenak77.hrdemo.exception.JdbcOptimisticLockException;
import com.github.teenak77.hrdemo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by teenak on 2015/02/07.
 */
@Repository
public class JdbcJobRepositoryImpl implements JobRepository {

    @Autowired
    MessageSource messageSource;

    private DataSource dataSource;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertJob;

    @Autowired
    public JdbcJobRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertJob = new SimpleJdbcInsert(dataSource)
                .withTableName("job")
                .usingGeneratedKeyColumns("key");

    }

    @Override
    public Collection<Job> findAll() {
        List<Job> job = this.namedParameterJdbcTemplate.query(
                "SELECT key, code, name, version FROM job",
                BeanPropertyRowMapper.newInstance(Job.class));
        return job;
    }

    @Override
    public Job findOne(Integer key) {
        Job job;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("key", key);
            job = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT key, code, name, version FROM job WHERE key=:key",
                    params,
                    BeanPropertyRowMapper.newInstance(Job.class));
        } catch (EmptyResultDataAccessException ex) {
            Locale locale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage("job.jobNotFound", new Object[]{key}, locale );
            throw new AppException(msg);
        }

        return job;
    }

    @Override
    public void save(Job job) {
        if(job.getKey() == null || job.getKey() == 0) {
            Number newKey = this.insertJob.executeAndReturnKey(
                    new BeanPropertySqlParameterSource(job));
            job.setKey(newKey.intValue());
            job.setVersion(1);
        } else {
            int affected = this.namedParameterJdbcTemplate.update(
                    "UPDATE job SET code=:code, name=:name, version=:version + 1" +
                            " WHERE key=:key and version=:version",
                    new BeanPropertySqlParameterSource(job));
            if( affected == 0 ) {
                throw new JdbcOptimisticLockException();
            }

        }

    }

    @Override
    public void delete(Job job) {
        Map params = new HashMap();
        params.put("key", job.getKey());
        params.put("version", job.getVersion());
        int affected = this.namedParameterJdbcTemplate.update(
                "DELETE FROM job " +
                        " WHERE key=:key and version=:version",
                params);
        if( affected == 0 ) {
            throw new JdbcOptimisticLockException();
        }
    }
}
