package com.example.project.HelperClass.Uploads;

public class NewActivityInstance {
    public String activity_name;
    public String activity_description;
    public int MachineId;
    public int ComponentId;
    public int ScheduleId;
    public String some_name;
    public void setActivity_name(String activity_name){
        this.activity_name=activity_name;
    }
    public String getActivity_name(){
        return activity_name;
    }

    public void setActivity_description(String activity_description){
        this.activity_description=activity_description;
    }
    public String getActivity_description(){
        return activity_description;
    }
    public int getMachineId(){
        return MachineId;
    }
    public void setMachineId(int MachineId){
        this.MachineId=MachineId;
    }
    public int getComponentId(){
        return ComponentId;
    }
    public void setComponentId(int ComponentId){
        this.ComponentId=ComponentId;
    }
    public int getScheduleId(){
        return ScheduleId;
    }
    public void setScheduleId(int ScheduleId){
        this.ScheduleId=ScheduleId;
    }
    public void setSome_name(String some_name){
        this.some_name=some_name;
    }
    public String getSome_name(){
        return some_name;
    }

}
