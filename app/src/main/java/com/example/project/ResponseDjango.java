package com.example.project;

import static com.example.project.createActivity.TAG;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
public class ResponseDjango {

    public HashMap<String, MachineData> response;

    public HashMap<String, MachineData> getResponse() {
        return response;
    }

    public void setResponse(HashMap<String, MachineData> response) {
        this.response = response;
    }
    public static class MachineData {
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
            public String machine_name;

            public String getMachine_name() {
                return machine_name;
            }

            public void setMachine_name(String machine_name) {
                this.machine_name = machine_name;
            }
        }
    }
}

