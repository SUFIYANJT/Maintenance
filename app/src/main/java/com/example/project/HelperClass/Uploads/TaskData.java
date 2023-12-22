package com.example.project.HelperClass.Uploads;

public class TaskData {
    private int task_activity_id;
    private String task_assigned_to;
    private String activity_name;
    private String description;
    private int machine_id;
    private int component_id;
    private int schedule_id;
    private final int status=2;

    public int getTask_activity_id() {
        return task_activity_id;
    }

    public void setTask_activity_id(int task_activity_id) {
        this.task_activity_id = task_activity_id;
    }

    public String getTask_assigned_to() {
        return task_assigned_to;
    }

    public void setTask_assigned_to(String task_assigned_to) {
        this.task_assigned_to = task_assigned_to;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public int getComponent_id() {
        return component_id;
    }

    public void setComponent_id(int component_id) {
        this.component_id = component_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }
}
