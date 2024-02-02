package com.example.project.HelperClass.Uploads;

import com.google.gson.annotations.SerializedName;

public class SubmitData {
    @SerializedName("submittext")
    private String submitText;

    @SerializedName("activity_id")
    private int activityId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("base64")
    private String base64;

    // Constructors, getters, and setters

    // Default constructor
    public SubmitData() {
    }

    // Parameterized constructor
    public SubmitData(String submitText, int activityId, int userId, String base64) {
        this.submitText = submitText;
        this.activityId = activityId;
        this.userId = userId;
        this.base64 = base64;
    }


    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getSubmitText() {
        return submitText;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}

