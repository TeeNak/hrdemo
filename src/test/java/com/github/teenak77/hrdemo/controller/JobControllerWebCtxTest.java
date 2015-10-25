package com.github.teenak77.hrdemo.controller;

import com.github.teenak77.hrdemo.model.domain.Job;
import com.github.teenak77.hrdemo.service.JobService;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        locations = {
                "classpath:spring/mvc-dispatcher-servlet.xml",
                "classpath:spring/test-job.xml"
        })
public class JobControllerWebCtxTest {

    @Autowired
    JobController jobController;

    @Autowired
    JobService jobServiceMock;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    public List<Job> GetJobList() {

        Job job1 = new Job();
        job1.setKey(1);
        job1.setCode("JOB001");
        job1.setName("Sales Person");
        job1.setVersion(1);

        Job job2 = new Job();
        job2.setKey(2);
        job2.setCode("JOB002");
        job2.setName("Sales Manager");
        job2.setVersion(1);

        List<Job> jobList = new ArrayList<Job>();
        jobList.add(job1);
        jobList.add(job2);

        return jobList;
    }

    @Before
    public void setUp() throws Exception {

        Mockito.reset(jobServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDispJobList() throws Exception {
        final String VIEW_JOB_LIST = "jobList";
        final String MODEL_ATTRIBUTE_JOB_LIST = "jobList";

        when(jobServiceMock.getAll()).thenReturn(GetJobList());
        mockMvc.perform(get("/Job/list"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_JOB_LIST))
                .andExpect(forwardedUrl("/WEB-INF/pages/jobList.jsp"))
                .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB_LIST, hasSize(2)))
                .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB_LIST, hasItem(
                        allOf(
                                hasProperty("key", is(1)),
                                hasProperty("code", is("JOB001")),
                                hasProperty("name", is("Sales Person"))
                        )
                )))
                .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB_LIST, hasItem(
                        allOf(
                                hasProperty("key", is(2)),
                                hasProperty("code", is("JOB002")),
                                hasProperty("name", is("Sales Manager"))
                        )
                )));

        verify(jobServiceMock).getAll();
        verifyNoMoreInteractions(jobServiceMock);

    }

    @Test
    public void testInitCreateJob() throws Exception {

        final String VIEW_JOB_NEW = "jobEdit";
        final String MODEL_ATTRIBUTE_JOB = "job";

        mockMvc.perform(get("/Job/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_JOB_NEW))
                .andExpect(forwardedUrl("/WEB-INF/pages/jobEdit.jsp"))
                .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("key", nullValue())))
                .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("code", isEmptyOrNullString())))
                .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("name", isEmptyOrNullString())))
                .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("version", isEmptyOrNullString())));

        verifyZeroInteractions(jobServiceMock);
    }

    @Test
    public void testProcessCreateJob_emptyJob() throws Exception {

        final String VIEW_JOB_NEW = "jobEdit";
        final String MODEL_ATTRIBUTE_JOB = "job";

        mockMvc.perform(
            post("/Job/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr(MODEL_ATTRIBUTE_JOB, new Job())
        )
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_JOB_NEW))
            .andExpect(forwardedUrl("/WEB-INF/pages/jobEdit.jsp"))
            .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_JOB, "code"))
            .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_JOB, "name"))
            .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("key", nullValue())))
            .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("code", isEmptyOrNullString())))
            .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("name", isEmptyOrNullString())))
            .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("version", isEmptyOrNullString())));

        verifyZeroInteractions(jobServiceMock);

    }

    @Test
    public void testProcessCreateJob_codeIsTooLong() throws Exception {

        String code = StringUtils.repeat("a", Job.MAX_LENGTH_CODE + 1);
        String name = StringUtils.repeat("b", Job.MAX_LENGTH_NAME + 1);

        String FORM_FIELD_CODE = "code";
        String FORM_FIELD_NAME = "name";

        final String VIEW_JOB_NEW = "jobEdit";
        final String MODEL_ATTRIBUTE_JOB = "job";

        mockMvc.perform(
            post("/Job/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(FORM_FIELD_CODE, code)
                .param(FORM_FIELD_NAME, name)
                .sessionAttr(MODEL_ATTRIBUTE_JOB, new Job())
        )
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_JOB_NEW))
            .andExpect(forwardedUrl("/WEB-INF/pages/jobEdit.jsp"))
            .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_JOB, "code"))
            .andExpect(model().attributeHasFieldErrors(MODEL_ATTRIBUTE_JOB, "name"))
            .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("key", nullValue())))
            .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("code", is(code))))
            .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("name", is(name))))
            .andExpect(model().attribute(MODEL_ATTRIBUTE_JOB, hasProperty("version", isEmptyOrNullString())));

        verifyZeroInteractions(jobServiceMock);

    }

    @Test
    public void testProcessCreateJob_normalCase() throws Exception {

        String code = "JOB765";
        String name = "Example Job";

        String FORM_FIELD_CODE = "code";
        String FORM_FIELD_NAME = "name";

        final String VIEW_JOB_LIST = "jobList";
        final String MODEL_ATTRIBUTE_JOB = "job";
        final String MODEL_ATTRIBUTE_JOB_LIST = "jobList";


        Job newJob = new Job.Builder()
                .code(code)
                .name(name)
                .build();

        mockMvc.perform(
                post("/Job/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param(FORM_FIELD_CODE, code)
                        .param(FORM_FIELD_NAME, name)
                     //   .sessionAttr(MODEL_ATTRIBUTE_JOB, new Job())
        )
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/Job/list"))
            .andExpect(redirectedUrl("/Job/list"))
        ;

        verify(jobServiceMock).add(eq(newJob));
        verifyNoMoreInteractions(jobServiceMock);


    }

    @Test
    public void testInitUpdateForm() throws Exception {

    }

    @Test
    public void testProcessUpdateForm() throws Exception {

    }

    @Test
    public void testDelete_normalCase() throws Exception {

        final String FORM_FIELD_KEY = "key";
        final String FORM_FIELD_CODE = "code";
        final String FORM_FIELD_NAME = "name";

        Integer key = 1;
        String code = "JOB001";
        String name = "Sales Person";


        mockMvc.perform(
                post("/Job/{jobKey}/delete", key)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param(FORM_FIELD_KEY, key.toString())
                        .param(FORM_FIELD_CODE, code)
                        .param(FORM_FIELD_NAME, name)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/Job/list"));


        Job deleted = new Job.Builder()
                .key(key)
                .code(code)
                .name(name)
                .build();

        verify(jobServiceMock).deleteOne(eq(deleted));
        verifyNoMoreInteractions(jobServiceMock);
    }
}