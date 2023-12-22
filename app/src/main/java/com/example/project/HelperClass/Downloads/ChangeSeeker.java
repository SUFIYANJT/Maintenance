package com.example.project.HelperClass.Downloads;

public class ChangeSeeker {
    ResponseData responseData;
    public ResponseData getResponseData(){
        return responseData;
    }
    public void setResponse(ResponseData response){
        this.responseData=response;
    }
   public static class ResponseData{
        private int change_seeker_id;
        private int change_activity_id;
        private String change_activity_type_id;
        private int position;
        public void setChange_seeker_id(int change_activity_id){
            this.change_seeker_id=change_activity_id;
        }
        public int getChange_seeker_id(){
            return change_seeker_id;
        }
        public void setChange_activity_id(int change_activity_id){
            this.change_activity_id=change_activity_id;
        }
        public int getChange_activity_id(){
            return change_activity_id;
        }
        public void setChange_activity_type_id(String change_activity_type_id){
            this.change_activity_type_id=change_activity_type_id;
        }
        public String  getChange_activity_type_id(){
            return change_activity_type_id;
        }
        public int getPosition(){
            return position;
        }
        public void setPosition(int position){
            this.position=position;
        }
    }

}
