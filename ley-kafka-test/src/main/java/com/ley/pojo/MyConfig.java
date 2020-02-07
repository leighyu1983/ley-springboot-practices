package com.ley.pojo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyConfig {
    private String desc = "初始值";

    /**
     *      * @Bean method MyConfig.initMethod is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface.
     *      * This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's
     *      * declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues;
     *
     * @return
     */
    //@Bean(initMethod="initNotManageBySpring")
    public static MyBean2 initMethod(){
        return new MyBean2();
    }

    @Bean(initMethod = "MyBean4Init")
    public MyBean4 getBean4(){
        return new MyBean4();
    }
}
