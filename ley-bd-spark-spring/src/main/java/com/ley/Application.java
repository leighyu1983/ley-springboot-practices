package com.ley;

import com.ley.config.MySparkConfig;
import com.ley.service.SparkBusinessEntry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    /**
     * NOT WORKING!!!
     *
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        //ac.register(MySparkConfig.class);
        ac.scan("com.ley");
        ac.refresh();

        SparkBusinessEntry entry = ac.getBean(SparkBusinessEntry.class);
        entry.select();
    }
}
