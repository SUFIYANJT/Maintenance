package com.example.project.HelperClass.Downloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ResponseSchedule {
    public HashMap<String, ScheduleData> response;

    public HashMap<String, ScheduleData> getResponse() {
        return response;
    }

    public void setResponse(HashMap<String,ScheduleData> response) {
        this.response = response;
    }
    public static class ScheduleData {
        @SerializedName("model")
        @Expose
        public String model;
        @SerializedName("pk")
        @Expose
        public int pk;
        @SerializedName("fields")
        @Expose
        public Fields fields;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getPk() {
            return pk;
        }

        public void setPk(int pk) {
            this.pk = pk;
        }

        public Fields getFields() {
            return fields;
        }

        public void setFields(Fields fields) {
            this.fields = fields;
        }

        public static class Fields {

            @SerializedName("machine_name")
            private String schedule_type;
            @JsonProperty("schedule_value")
            private int scheduleValue;

            public String getSchedule_type() {
                return schedule_type;
            }

            public void setSchedule_type(String schedule_type) {
                this.schedule_type = schedule_type;
            }

            public int getSchedule_values() {
                return scheduleValue;
            }

            public void setSchedule_values(int schedule_values) {
                this.scheduleValue = schedule_values;
            }
        }
    }
}
