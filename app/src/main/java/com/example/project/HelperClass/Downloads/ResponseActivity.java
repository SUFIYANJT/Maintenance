package com.example.project.HelperClass.Downloads;

import java.util.Date;
import java.util.HashMap;

public class ResponseActivity {
    HashMap<String,ActivityData> response;
    public HashMap<String,ActivityData> getResponse(){
        return response;
    }
    public void setResponse(HashMap<String,ActivityData> response){
        this.response=response;
    }
    public static class ActivityData{
        private String model;
        private int pk;
        private Fields fields;
        public void setModel(String model){
            this.model=model;
        }
        public String getModel(){
            return model;
        }
        public void setFields(Fields fields){
            this.fields=fields;
        }
        public Fields getFields(){
            return fields;
        }
        public void setPk(int pk){
            this.pk=pk;
        }
        public int getPk(){
            return pk;
        }
        public static class Fields{
            private int activity_id;
            private String activity_name;
            private String activity_description;
            private int activity_machine_id;
            private int activity_component_id;
            private int activity_schedule_id;
            private int activity_status_id;
            private Date activity_issued_date;
            public void setActivity_status_id(int activity_status_id){
                this.activity_status_id=activity_status_id;
            }
            public int getActivity_status_id(){
                return activity_status_id;
            }
            public String getActivity_name(){
                return activity_name;
            }
            public void setActivity_name(String activity_name){
                this.activity_name=activity_name;
            }
            public String getActivity_description(){
                return activity_description;
            }
            public void setActivity_description(String activity_description){
                this.activity_description=activity_description;
            }
            public void setActivity_machine_id(int machineId){
                this.activity_machine_id=machineId;
            }
            public int getActivity_machine_id(){
                return activity_machine_id;
            }
            public void setActivity_component_id(int componentId){
                this.activity_component_id=componentId;
            }
            public int getActivity_component_id(){
                return activity_component_id;
            }
            public void setActivity_schedule_id(int scheduleId){
                this.activity_schedule_id=scheduleId;
            }
            public int getActivity_schedule_id(){
                return activity_schedule_id;
            }

            public Date getActivity_issued_date() {
                return activity_issued_date;
            }

            public void setActivity_issued_date(Date activity_issued_date) {
                this.activity_issued_date = activity_issued_date;
            }

            public int getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(int activity_id) {
                this.activity_id = activity_id;
            }
        }
    }

}
