package com.ley.scenario.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainEntry {

    @Autowired private School schoola;

    /**
     * BeanFactory @Bean -> FactoryBean with &; @Component -> normalBean without &
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.scan("com.ley.scenario.bean");
        ac.refresh();

        FactoryBean bf;
        Person person = ac.getBean("myPerson", Person.class);
        person.setFirstName("haha");
        System.out.println(" this is my first name " + person.getFirstName());

        User user = ac.getBean("user", User.class);
        user.setId("heiehi");
        System.out.println(" this is my id " + user.getId());

        School school = ac.getBean("school", School.class);
        System.out.println(" school bean area " + school.getArea());

        SchoolFactoryBean schoolFactoryBean = ac.getBean("&school", SchoolFactoryBean.class);
        System.out.println(" school factory bean area " + schoolFactoryBean.getObject().getArea());


        BusinessEntry be = ac.getBean("businessEntry", BusinessEntry.class);
        be.good();
    }
}
