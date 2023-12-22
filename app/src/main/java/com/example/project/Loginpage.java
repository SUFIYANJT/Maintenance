package com.example.project;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.HelperClass.Downloads.LoginRes;
import com.example.project.HelperClass.Uploads.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginpage extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button btnLogin;
    public static String TAG="ContentValues";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the following with your authentication logic
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
               /* Intent intent=new Intent(Loginpage.this,MainActivity.class);
                startActivity(intent);*/
                LoginData loginData=new LoginData();
                loginData.setUsername(username);
                loginData.setPassword(password);
                ApiService apiService=RetrofitClient.getApiService();
                Call<LoginRes> loginDataCall=apiService.checkUser(loginData);
                loginDataCall.enqueue(new Callback<LoginRes>() {
                    @Override
                    public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: is successful"+(char)response.body().getUserMode()+" "+response.body().getUserID());
                            if(response.body()!=null){
                                LoginRes res=response.body();
                                if((char)res.getUserMode()=='A') {
                                    Intent intent = new Intent(Loginpage.this, MainActivity.class);
                                    intent.putExtra("username",res.getUsername());
                                    intent.putExtra("userid",res.getUserID());
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(Loginpage.this, InspectorMode.class);
                                    intent.putExtra("username",res.getUsername());
                                    intent.putExtra("userid",res.getUserID());
                                    startActivity(intent);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRes> call, Throwable t) {
                        Log.e(TAG, "onFailure: ",t );
                    }
                });

            }
        });
    }
}
