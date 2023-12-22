package com.example.project;

import java.util.Date;

public class PendingData {
    private int id;
    public String name;
    public String description;
    public String history;

    public int machine_id;
    public int component_id;
    public int schedule_id;
    private Date issued_date;
    private String status_name;
    public int getMachine_id(){
        return machine_id;
    }
    public int getComponent_id(){
        return component_id;
    }
    public int getSchedule_id(){
        return schedule_id;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public String getHistory(){
        return this.history;
    }
    public int status_id;
    public int getStatus_id() {
        return status_id;
    }
    public void setStatus_id(int status_id){
        this.status_id=status_id;
    }
    public void setData(int id,String name,String description,String history,int machine_id,int component_id,int schedule_id,int status_id,Date issued_date){
        this.name=name;
        this.id=id;
        this.description=description;
        this.history=history;
        this.machine_id=machine_id;
        this.component_id=component_id;
        this.schedule_id=schedule_id;
        this.status_id=status_id;
        this.issued_date=issued_date;
    }

    public Date getIssued_date() {
        return issued_date;
    }

    public void setIssued_date(Date issued_date) {
        this.issued_date = issued_date;
    }

    public String getStatus_name() {
        return status_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
