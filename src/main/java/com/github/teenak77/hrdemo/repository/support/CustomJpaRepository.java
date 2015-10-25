package com.github.teenak77.hrdemo.repository.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by teenak on 2015/02/19.
 */
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {



}
