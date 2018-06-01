package com.jz.snake.important;

import org.nutz.integration.shiro.ShiroSessionProvider;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * 系统程序加载入口
 * Created by jzshi on 2018/5/21.
 */
@Modules(scanPackage = true, packages="com.jz.snake")
@Ok("json:full")
@Fail("http:500")
@IocBy(type = ComboIocProvider.class, args = { "*json", "config/", "ioc/",
        "*anno", "com.jz.snake",
        "*tx",
        "*quartz",
        "*jedis"})
//@Localization(value = "locales/", defaultLocalizationKey = "zh_CN")
@Encoding(input = "UTF-8", output = "UTF-8")
@SetupBy(value=MainModuleSetup.class)
@SessionBy(ShiroSessionProvider.class)
//@ChainBy(args = "ioc/nutzwk-mvc-chain.json")
public class MainModule {
}
