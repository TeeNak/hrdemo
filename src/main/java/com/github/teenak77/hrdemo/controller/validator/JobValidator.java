package com.github.teenak77.hrdemo.controller.validator;

import com.github.teenak77.hrdemo.model.domain.Job;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by teenak on 2015/02/07.
 */
public class JobValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Job.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Job job = (Job) target;
        if(job == null) {
            errors.rejectValue("key","error.not-specified",null,"Value required.");
            return;
        }

        if(job.getCode() == null || job.getCode().isEmpty()) {
            errors.rejectValue("code","error.not-specified",null,"Value required.");
        }

        if(job.getName() == null || job.getName().isEmpty()) {
            errors.rejectValue("name","error.not-specified",null,"Value required.");
        }

    }
}
