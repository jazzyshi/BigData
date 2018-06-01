package com.jz.snake.important;

import com.jz.snake.important.utils.DaoUtils;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jzshi on 2018/5/21.
 */
public class MainModuleSetup implements Setup {
  //  private static final Logger logger = Logger.getLogger(MainModuleSetup.class);
    private List<SetupListener> setupListenerList =new ArrayList<>();
    private NutConfig config;
    @Override
    public void init(NutConfig nutConfig) {
        this.config = nutConfig;
        ServletContext ctx = config.getServletContext();

        //设置工作目录系统参数
        //为确保在所有SetupListener之前执行，没有通过SetupListener实现类实现
        System.setProperty("context.path", ctx.getContextPath());
        String webdir = new File(config.getAppRoot()).toPath().normalize().toString(); //未使用getCanonicalPath，避免需要捕获异常
        System.setProperty("web.dir", webdir);//当前应用的根路径
        System.setProperty("webinf.dir", webdir + File.separator + "WEB-INF");
        System.setProperty("conf.dir", webdir + File.separator + "WEB-INF" + File.separator + "classes" + File.separator+ "config");
        System.setProperty("server.ip", LocalAddressUtil.getIp());
        System.setProperty("server.hostname", LocalAddressUtil.getHostName());
        System.setProperty("server.starttime", String.valueOf(System.currentTimeMillis()));

        //为确保DaoUtil在所有的伴随启动初始化类之前初始化，避免某些$startup_由于名称原因在DaoUtil之前初始化，从而造成调出出错
        DaoUtils.getDaoUtils().setIoc(config.getIoc());

        String[] names = config.getIoc().getNames();
        for(String name : names){
            System.out.println("name="+name);
        }

        // 触发quartz 工厂,将扫描job任务
        config.getIoc().get(NutQuartzCronJobFactory.class);

    }

    @Override
    public void destroy(NutConfig nutConfig) {

    }
}
