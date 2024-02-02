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

import com.example.project.HelperClass.DataHolder.LoginDetails;
import com.example.project.HelperClass.DataHolder.SharedPreferenceManager;
import com.example.project.HelperClass.Downloads.LoginRes;
import com.example.project.HelperClass.Uploads.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginpage extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button btnLogin;
    LoginData loginData;
    public static String TAG="ContentValues";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        LoginDetails loginDetails=SharedPreferenceManager.getLoginDetails(Loginpage.this);
        Log.d(TAG, "onCreate: "+loginDetails.getUsername()+" "+loginDetails.getPassword());
        btnLogin.setVisibility(View.INVISIBLE);
        editTextPassword.setVisibility(View.INVISIBLE);
        editTextUsername.setVisibility(View.INVISIBLE);
        if(loginDetails.getPassword()!=null)
            Authenicate(loginDetails.getUsername(),loginDetails.getPassword());
        else{
            btnLogin.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            editTextUsername.setVisibility(View.VISIBLE);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the following with your authentication logic
                String username = editTextUsername.getText().toString().replace(" ","");
                String password = editTextPassword.getText().toString().replace(" ","");
               /* Intent intent=new Intent(Loginpage.this,MainActivity.class);
                startActivity(intent);*/

                loginData = new LoginData();
                loginData.setUsername(username);
                loginData.setPassword(password);
                Authenicate(username,password);

            }
        });
    }
    public void Authenicate(String username,String password){
        loginData = new LoginData();
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
                        SharedPreferenceManager.saveLoginDetails(Loginpage.this,username,password);
                        if((char)res.getUserMode()=='A') {
                            Intent intent = new Intent(Loginpage.this, MainActivity.class);
                            intent.putExtra("username",res.getUsername());
                            intent.putExtra("userid",res.getUserID());
                            startActivity(intent);
                        }else if((char)res.getUserMode()=='B'){
                            Intent intent = new Intent(Loginpage.this, InspectorMode.class);
                            intent.putExtra("username",res.getUsername());
                            intent.putExtra("userid",res.getUserID());
                            startActivity(intent);
                        }else{
                            btnLogin.setVisibility(View.VISIBLE);
                            editTextPassword.setVisibility(View.INVISIBLE);
                            editTextUsername.setVisibility(View.INVISIBLE);
                        }
                    }
                }else{
                    btnLogin.setVisibility(View.VISIBLE);
                    editTextPassword.setVisibility(View.INVISIBLE);
                    editTextUsername.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
                btnLogin.setVisibility(View.VISIBLE);
                editTextPassword.setVisibility(View.INVISIBLE);
                editTextUsername.setVisibility(View.INVISIBLE);
            }
        });
    }
}
