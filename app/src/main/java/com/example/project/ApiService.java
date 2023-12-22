package com.example.project;
import com.example.project.HelperClass.Downloads.ChangeSeeker;
import com.example.project.HelperClass.Downloads.LoginRes;
import com.example.project.HelperClass.Downloads.ResponseActivity;
import com.example.project.HelperClass.Downloads.ResponseComponent;
import com.example.project.HelperClass.Downloads.ResponseDjango;
import com.example.project.HelperClass.Downloads.ResponseSchedule;
import com.example.project.HelperClass.Downloads.UserDatas;
import com.example.project.HelperClass.Uploads.LoginData;
import com.example.project.HelperClass.Uploads.NewActivityInstance;
import com.example.project.HelperClass.Uploads.SubmitData;
import com.example.project.HelperClass.Uploads.TaskData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/home/Machine{userId}")
    Call<ResponseDjango> getUser(@Path("userId") String userId);
    @GET("/home/Component{userId}")
    Call<ResponseComponent> getComponent(@Path("userId") String userId);
    @GET("/home/Schedule{userId}")
    Call<ResponseSchedule> getSchedule(@Path("userId") String userId);
    @POST("/home/update")
    Call<Integer> sendActivityInstance(@Body NewActivityInstance newActivityInstance);
    @GET("/home/Activity")
    Call<ResponseActivity> getActivityData();
    @GET("/home/check{flag}")
    Call<Integer> check(@Path("flag") String flag);
    @GET("/home/checkdouble")
    Call<ChangeSeeker> getChangeSeeker();
    @POST("/home/insertLast")
    Call<ResponseActivity.ActivityData.Fields> checkInsert(@Body Integer id);
    @GET("/home/updateLast/?id={id}")
    Call<ResponseActivity.ActivityData.Fields> checkUpdate(@Path("id") int id);

    @POST("/home/checkTask")
    Call<ResponseActivity> getTask(@Body Integer id);

    @POST("/home/login")
    Call<LoginRes> checkUser(@Body LoginData loginData);

    @POST("/home/submit")
    Call<Integer> submit(@Body SubmitData submitData);

    @POST("/home/users")
    Call<UserDatas> getUsers(@Body String search);

    @POST("/home/uploadAssignment")
    Call<Integer> uploadAssignment(@Body TaskData taskData);
    @POST("/home/reportView")
    Call<ReportObj> getReport(@Body int id);

    @POST("/home/activityUpdate")
    Call<Integer> updateActivity(@Body int id);

}
