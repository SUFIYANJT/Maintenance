package com.example.project;

import static com.example.project.Loginpage.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.project.HelperClass.DataHolder.BorderTransformation;
import com.example.project.HelperClass.Downloads.LoginRes;
import com.example.project.HelperClass.Uploads.SubmitData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitReport extends AppCompatActivity {

    EditText editText;
    int userid;
    int activityid;
    ImageView imageView;
    Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        editText=findViewById(R.id.editTextsumbit);
        Button button=findViewById(R.id.submitReport);
        int maxCharactersPerLine = 10;
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // Iterate through the characters being added
                for (int i = start; i < end; i++) {
                    // Check if the character is a newline character
                    if (source.charAt(i) == '\n') {
                        // Calculate the current line number
                        int currentLine = editText.getLayout().getLineForOffset(dstart + i);

                        // Check if the line exceeds the maximum characters
                        if (editText.getLayout().getLineEnd(currentLine) > dstart + i) {
                            return ""; // Ignore newline character
                        }
                    }
                }
                // Accept the characters being added
                return null;
            }
        };
        imageView=findViewById(R.id.imagePreview);
        editText.setFilters(new InputFilter[]{inputFilter});
        Intent intent=getIntent();
        FloatingActionButton floatingActionButton=findViewById(R.id.floatingImage);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        if(intent!=null){
            userid=getIntent().getIntExtra("userid",0);
            activityid=getIntent().getIntExtra("activityid",0);
            Log.d(TAG, "onCreate: "+userid);
        }
        button.setOnClickListener(v -> {
            Log.d(TAG, "onCreate: clicked");
            Toast.makeText(SubmitReport.this,"clicked",Toast.LENGTH_SHORT).show();
            ApiService apiService=RetrofitClient.getApiService();
            SubmitData submitData=new SubmitData();
            submitData.setSubmitText(editText.getText().toString());
            submitData.setActivityId(activityid);
            submitData.setUserId(userid);
            Log.d(TAG, "onCreate: "+submitData.getUserId());
            String base64=null;
            try {
                ContentResolver contentResolver = SubmitReport.this.getContentResolver();
                InputStream inputStream = contentResolver.openInputStream(selectedImageUri);

                if (inputStream != null) {
                    // Read bytes from the input stream
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    base64=Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.d(TAG, "onCreate:  base64"+base64);
            submitData.setBase64(base64);
            Call<Integer> loginDataCall=apiService.submit(submitData);
            loginDataCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if(response.isSuccessful()){
                        int i=response.body();
                        if(i==100){
                            editText.setVisibility(View.INVISIBLE);
                            Toast.makeText(SubmitReport.this, "REPORT SUMBITTED", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.d(TAG, "onResponse: response was not successfull");
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e(TAG, "onFailure: ",t );
                }
            });
        });
    }
    @SuppressLint("CheckResult")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            // This is the URI of the image chosen by the user

            selectedImageUri = data.getData();
            //new BlurTransformation(5,3)
            RequestOptions options = new RequestOptions();
            options.centerCrop().transform(new RoundedCorners(20));
            // Load the selected image into the ImageView using Glide or another image loading library
            Glide.with(this)
                    .load(selectedImageUri)
                    .apply(options)
                    .into(imageView);
        }
    }
}