package com.example.project;

public class DataTable {
    public String name;
    public String description;
    public String history;
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public String getHistory(){
        return this.history;
    }
    public void setData(String name,String description,String history){
        this.name=name;
        this.description=description;
        this.history=history;
    }
}
