package com.jz.snake.important.jobs;

import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by jzshi on 2018/5/29.
 */
@IocBean
public class Job2 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("我要干");
    }
}
