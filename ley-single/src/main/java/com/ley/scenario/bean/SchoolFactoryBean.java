package com.ley.scenario.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.scheduling.annotation.Scheduled;

public class SchoolFactoryBean implements FactoryBean<School> {
    private int factoryId;
    private String area;

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public School getObject() throws Exception {
        return new School(this.area);
    }

    @Override
    public Class<?> getObjectType() {
        return Scheduled.class;
    }
}
