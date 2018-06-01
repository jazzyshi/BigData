var ioc = {
      dataSource : {
          type : "com.alibaba.druid.pool.DruidDataSource",
          events : {
              create : "init",
              depose : 'close'
          },
          fields : {
              url : {java:"$conf.get('db.url')"},
              username : {java:"$conf.get('db.username')"},
              password : {java:"$conf.get('db.password')"},
              testWhileIdle : true,
              validationQuery : {java:"$conf.get('db.validationQuery')"},
              maxActive : {java:"$conf.get('db.maxActive')"},
              filters : "mergeStat",
              connectionProperties : "druid.stat.slowSqlMillis=1000"
          }
      },
      dao : {
              type : "org.nutz.dao.impl.NutDao",
              args : [{refer:"dataSource"}]
      }
};