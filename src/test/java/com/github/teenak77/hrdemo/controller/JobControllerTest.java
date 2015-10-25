package com.github.teenak77.hrdemo.controller;

import com.github.teenak77.hrdemo.model.domain.Job;
import com.github.teenak77.hrdemo.service.JobService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by teenak on 2015/02/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class JobControllerTest {

    @Mock
    JobService jobServiceMock;

    @InjectMocks
    JobController jobController;


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
        //Mockito.reset(jobServiceMock);
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDispJobList() throws Exception {
        final String VIEW_JOB_LIST = "jobList";
        final String MODEL_ATTRIBUTE_JOB_LIST = "jobList";

        when(jobServiceMock.getAll()).thenReturn(GetJobList());

        Map<String, Object> models = new HashMap<String, Object>();

        String view = jobController.dispJobList(models);

        assertThat(view, is(VIEW_JOB_LIST));
        List<Job> jobList = (List<Job>)models.get(MODEL_ATTRIBUTE_JOB_LIST);
        assertThat(jobList, is(equalTo(GetJobList())));

        verify(jobServiceMock).getAll();
        verifyNoMoreInteractions(jobServiceMock);
    }

}
