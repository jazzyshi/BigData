var ioc = {
    // 直接初始化Ehcache,默认找ehcache.xml文件哦
    /**
    cacheManager : {
        type : "net.sf.ehcache.CacheManager",
        factory : "net.sf.ehcache.CacheManager#create"
    }
     **/
    // 与shiro共享一个ehcache示例的方式
     cacheManager : {
     type : "net.sf.ehcache.CacheManager",
     factory : "net.sf.ehcache.CacheManager#getCacheManager",
     args : ["nutzbook"] // 对应shiro.ini中指定的ehcache.xml中定义的name
     }
};