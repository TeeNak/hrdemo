package com.github.teenak77.hrdemo.model.domain;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Created by teenak on 2015/02/07.
 */
@Entity
@Table(name = "job")
public class Job {

    public static final int MAX_LENGTH_CODE = 30;
    public static final int MAX_LENGTH_NAME = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer key;

    @Length(max = MAX_LENGTH_CODE)
    @Column(name = "code")
    String code;

    @Length(max = MAX_LENGTH_NAME)
    @Column(name = "name")
    String name;

    @Version
    @Column(name="version")
    Integer version;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return StringUtils.join(getName(),":",getCode());
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }


    public static class Builder {

        private Job built;

        public Builder() {
            built = new Job();
        }

        public Job build() {
            return built;
        }

        public Builder key(Integer key) {
            built.key = key;
            return this;
        }

        public Builder code(String code) {
            built.code = code;
            return this;
        }

        public Builder name(String name) {
            built.name = name;
            return this;
        }

        public Builder version(Integer version) {
            built.version = version;
            return this;
        }

    }
}
