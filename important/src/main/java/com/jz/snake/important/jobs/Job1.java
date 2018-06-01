package com.jz.snake.important.jobs;

import org.nutz.integration.quartz.annotation.Scheduled;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by jzshi on 2018/5/29.
 */
@IocBean
@Scheduled(cron="0 * * * * ?")//直接使用注解来声明cron
public class Job1 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("就是干");
    }
}
