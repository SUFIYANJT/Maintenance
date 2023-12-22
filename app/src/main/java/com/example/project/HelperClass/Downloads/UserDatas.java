package com.example.project.HelperClass.Downloads;

import java.util.HashMap;

public class UserDatas {
    HashMap<String, Users> response;
    public HashMap<String, Users> getResponse(){
        return response;
    }
    public void setResponse(HashMap<String,Users> response){
        this.response=response;
    }
    public static class Users{
        private String model;
        private int pk;
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
            private String user_name;
            private String user_password;
            private String user_mode;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_password() {
                return user_password;
            }

            public void setUser_password(String user_password) {
                this.user_password = user_password;
            }

            public String getUser_mode() {
                return user_mode;
            }

            public void setUser_mode(String user_mode) {
                this.user_mode = user_mode;
            }
        }
    }
}
