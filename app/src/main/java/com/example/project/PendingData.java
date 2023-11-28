package com.example.project;

public class PendingData {
    int progress;
    String activity_name;
    String description;
    String report;
    String status;
    public String getDescription(){
        return description;
    }
    public String getActivity_name(){
        return activity_name;
    }
    public String getReport(){
        return report;
    }
    public String getStatus(){
        return status;
    }
    public void setData(int progress,String description,String activity_name){
        this.activity_name=activity_name;
        this.description=description;
        this.progress=progress;
    }
    public void setData(String status,String description,String activity_name){
        this.activity_name=activity_name;
        this.description=description;
        this.status=status;
    }
}
