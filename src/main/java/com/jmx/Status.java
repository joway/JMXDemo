package com.jmx;

/**
 * Created by joway on 16/2/11.
 */
public class Status implements StatusMBean {
    private String status;
    private String name;

    public Status(String status , String name){
        this.status = status;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}
