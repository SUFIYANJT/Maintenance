package com.example.project.HelperClass.Downloads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ResponseComponent {

    private HashMap<String,ComponentData > response;

    public HashMap<String, ComponentData> getResponse() {
        return response;
    }

    public void setResponse(HashMap<String,ComponentData> response) {
        this.response = response;
    }
    public static class ComponentData {
        @SerializedName("model")
        @Expose
        private String model;
        @SerializedName("pk")
        @Expose
        private int pk;
        @SerializedName("fields")
        @Expose
        private Fields fields;

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

            @SerializedName("component_name")
            private String component_name;

            public String getComponent_name() {
                return component_name;
            }

            public void setComponent_name(String component_name) {
                this.component_name = component_name;
            }
        }
    }
}
