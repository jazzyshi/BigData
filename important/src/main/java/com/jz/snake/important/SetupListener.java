/**
 * Copyright: 瀚思安信（北京）软件技术有限公司，保留所有权利。
 * Author: FENGQC
 * Created: 2016年7月20日
 */
package com.jz.snake.important;

import org.nutz.mvc.NutConfig;

/**
 * 实现类继承此接口，并实现所有方法后，还必须在Ioc中配置实例，并且实例的name必须以'$setup_'开头
 * Author: jzshi
 * Created: 2018年5月21日
 */
public interface SetupListener {
	
	/**
	 * 启动时执行
	 * @param config
	 */
	public void init(NutConfig config);

	/**
	 * 停止时执行
	 * @param config
	 */
	public void destroy(NutConfig config);
	
	/**
	 * 受限制字段，用于匹配license，如果不符合则不加载
	 * @return
	 */
	public String restrictedTo();
	
	/**
	 * 加载优先级，正整数，数字越小越早启动
	 * @return
	 */
	public int priority();
	
	/**
	 * 加载先于指定的监听器名称
	 * @return
	 */
	public String before();
	
	/**
	 * 加载晚于指定的监听器名称
	 * @return
	 */
	public String after();
}
