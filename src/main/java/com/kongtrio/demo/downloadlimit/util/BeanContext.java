package com.kongtrio.demo.downloadlimit.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class BeanContext implements ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(BeanContext.class);
	
	public static ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		logger.debug("注入ApplicationContext到BeanContext:{}", context);

        if (BeanContext.ctx != null) {
            logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + BeanContext.ctx);
        }

        BeanContext.ctx = context;
	}
	
	public static Object getBean(String beanName) {
		assertContextInjected();
		return ctx.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		assertContextInjected();
		return ctx.getBean(clazz);
	}
	
	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		assertContextInjected();
		Map<String, T> map = ctx.getBeansOfType(type);
		if (map == null) {
			map = new HashMap<String, T>();
		}
		return map;
	}

	 /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        Validate.validState(ctx != null,
                "applicaitonContext属性未注入, 请在applicationContext.xml中定义BeanContext.");
    }
}