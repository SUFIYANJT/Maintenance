package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class DataTable implements Parcelable {
    private int activity_id;
    public String name;
    public String description;
    public String history;

    public int machine_id;
    public int component_id;
    public int schedule_id;
    public int status_id;
    private Date issued_date;
    private int time;

    public int getStatus_id() {
        return status_id;
    }
     public void setStatus_id(int status_id){
        this.status_id=status_id;
     }
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
    DataTable(Parcel in) {
        name = in.readString();
        description = in.readString();
        history=in.readString();
        machine_id=in.readInt();
        component_id=in.readInt();
        schedule_id=in.readInt();
        status_id=in.readInt();
    }
    public DataTable(int activity_id,String name, String description, String history, int machine_id, int component_id, int schedule_id, int status_id,Date issued_date){
        this.activity_id=activity_id;
        this.name=name;
        this.description=description;
        this.history=history;
        this.machine_id=machine_id;
        this.component_id=component_id;
        this.schedule_id=schedule_id;
        this.status_id=status_id;
        this.issued_date=issued_date;
        getTime();
    }
    public static final Parcelable.Creator<DataTable> CREATOR = new Parcelable.Creator<DataTable>() {
        public DataTable createFromParcel(Parcel in) {
            return new DataTable(in);
        }

        public DataTable[] newArray(int size) {
            return new DataTable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(history);
        dest.writeInt(machine_id);
        dest.writeInt(component_id);
        dest.writeInt(schedule_id);
        dest.writeInt(status_id);
    }

    public Date getIssued_date() {
        return issued_date;
    }

    public void setIssued_date(Date issued_date) {
        this.issued_date = issued_date;
    }

    public int getTime() {
        Date d=new Date();
        long differenceInMillis=d.getTime()-getIssued_date().getTime();
        long differenceInSeconds = differenceInMillis / 1000;
        long differenceInMinutes = differenceInSeconds / 60;
        long differenceInHours = differenceInMinutes / 60;
        long differenceInDays = differenceInHours / 24;
        int scheduledDay=0;
        if(schedule_id==1){
            scheduledDay=7;
        }else if(schedule_id==2){
            scheduledDay=30;
        }
        time=(int)(scheduledDay-differenceInDays);
        return time;
    }

    public int getActivity_id() {
        return activity_id;
    }
}
