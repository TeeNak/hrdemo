package com.github.teenak77.hrdemo.controller;


import com.github.teenak77.hrdemo.controller.validator.JobValidator;
import com.github.teenak77.hrdemo.exception.ResourceNotFoundException;
import com.github.teenak77.hrdemo.model.domain.Job;
import com.github.teenak77.hrdemo.exception.JdbcOptimisticLockException;
import com.github.teenak77.hrdemo.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

/**
 * Created by teenak on 2015/02/07.
 */
@Controller
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private JobService jobService;

    private MessageSource messageSource;

    @Autowired
    public JobController(JobService jobService, MessageSource messageSource) {
        this.jobService = jobService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/secure/Job/list", method = RequestMethod.GET)
    public String  dispJobList(Map<String, Object> model) {
        model.put("jobList", jobService.getAll());
        return "jobList";
    }

    @RequestMapping(value = "/secure/Job/new", method = RequestMethod.GET)
    public String  initCreateJob(Map<String, Object> model) {
        Job job = new Job();
        model.put("job", job);
        return "jobEdit";
    }

    @RequestMapping(value = "/secure/Job/new", method = RequestMethod.POST)
    public String  processCreateJob(
            @Valid @ModelAttribute("job") Job job,
            BindingResult result,
            SessionStatus status) {

        new JobValidator().validate(job, result);
        if (result.hasErrors()) {
            return "jobEdit";
        }

        jobService.add(job);
        status.setComplete();
        return "redirect:/secure/Job/list";
    }

    @RequestMapping(value = "/secure/Job/{jobKey}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("jobKey") int key, Map<String, Object> model) {
        Job job = jobService.getByKey(key);
        if( job == null ) {
            throw new ResourceNotFoundException();
        }

        model.put("job", job);
        return "jobEdit";
    }

    @RequestMapping(value = "/secure/Job/{jobKey}/edit", method = RequestMethod.POST)
    public String processUpdateForm(
            @PathVariable("jobKey") int key,
            @Valid @ModelAttribute("job") Job job,
            BindingResult result,
            SessionStatus status) {
        if(job == null) {
            throw new RuntimeException("Something is wrong in submitted form.");
        }

        if(key != job.getKey()) {
            throw new RuntimeException("Something is wrong in key.");
        }

        new JobValidator().validate(job, result);
        if (result.hasErrors()) {
            return "jobEdit";
        }

        try {

            this.jobService.updateOne(job);

        } catch (JdbcOptimisticLockException e) {
            // for jdbc
            result.rejectValue("version","error.optimisticLock",null,"Optimistic Lock Error.");
            return "jobEdit";
        } catch (ObjectOptimisticLockingFailureException e) {
            // for jpa
            result.rejectValue("version","error.optimisticLock",null,"Optimistic Lock Error.");
            return "jobEdit";
        }
        status.setComplete();
        return "redirect:/secure/Job/list";

    }
/*
    @RequestMapping(value = "/Job/{jobKey}/delete", method = RequestMethod.GET)
    public String delete1(@PathVariable("jobKey") int key, Map<String, Object> model) {
        jobService.delete(key);
        return "redirect:/Job/list";
    }
*/
    @RequestMapping(value = "/secure/Job/{jobKey}/delete", method = RequestMethod.POST)
    public String delete(
            @PathVariable("jobKey") int key,
            @ModelAttribute("job") Job job,
            BindingResult result,
            Map<String, Object> model,
            RedirectAttributes attributes,
            SessionStatus status) {

        if(job == null) {
            throw new RuntimeException("Something is wrong in submitted form.");
        }

        if(key != job.getKey()) {
            throw new RuntimeException("Something is wrong in key.");
        }

        try {

            jobService.deleteOne(job);

        } catch (JdbcOptimisticLockException e) {
            // for jdbc
            model.put("deleting", "1");
            result.rejectValue("version","error.optimisticLock",null,"Optimistic Lock Error.");
            return "jobEdit";
        } catch (ObjectOptimisticLockingFailureException e) {
            // for jpa
            model.put("deleting", 1);
            result.rejectValue("version","error.optimisticLock",null,"Optimistic Lock Error.");
            return "jobEdit";
        }
/*
        } catch (JdbcOptimisticLockException e) {
            // for jdbc
            String localizedFeedbackMessage = getMessage("error.optimisticLock", null, "Optimistic Lock Error.");
            attributes.addFlashAttribute("errorMessage", localizedFeedbackMessage);
            attributes.addAttribute("jobKey", key);
            return "redirect:/secure/Job/{jobKey}/edit";
        } catch (ObjectOptimisticLockingFailureException e) {
            // for jpa
            String localizedFeedbackMessage = getMessage("error.optimisticLock", null, "Optimistic Lock Error.");
            attributes.addFlashAttribute("errorMessage", localizedFeedbackMessage);
            attributes.addAttribute("jobKey", key);
            return "redirect:/secure/Job/{jobKey}/edit";
        }
*/
        return "redirect:/secure/Job/list";
    }

    private String getMessage(String messageCode, Object... messageParameters) {
        Locale current = LocaleContextHolder.getLocale();
        logger.debug("Current locale is {}", current);
        return messageSource.getMessage(messageCode, messageParameters, current);
    }
}
