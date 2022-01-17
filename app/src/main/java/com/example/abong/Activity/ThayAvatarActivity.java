package com.example.abong.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.abong.Api.ApiService;
import com.example.abong.Fragment.AccountFragment;
import com.example.abong.R;
import com.example.abong.data_local.DataLocalManager;
import com.example.abong.modle.User;
import com.example.abong.ultil.RealPathUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  ThayAvatarActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    private static final int MY_REQUEST_CODE = 132;
    private ImageView imgChooseAvt, btnBack;
    private Button btnChooseAvt, btnSelectAvt;
    private Uri mUri;
    private String urlAvatar, username;
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG,"onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data == null){
                            return;
                        }
                        Uri uri = data.getData(); // dữ liệu ảnh từ gallery
                        mUri = uri;
//                        realpath = getRealPathFromURI(uri); //tạo ảnh thành đường dẫn
//                        Log.e(TAG,realpath);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgChooseAvt.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thayavatar);

        AnhXa();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnChooseAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnSelectAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strRealPath = RealPathUtil.getRealPath(getApplicationContext(),mUri);
//                Log.e("ABONG",strRealPath);
                File file = new File(strRealPath);
                String [] mangtenfile = strRealPath.split("\\."); // cắt đường dẫn từ dấu "." thành 2 phần
                strRealPath = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];

                RequestBody requestAvatar = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_avatar",strRealPath,requestAvatar);
                ApiService.apiService.uploadAvatar(body).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String message = response.body();
                        if (message != null){
//                            Log.d("BBB",message);
                            User user = DataLocalManager.getUser();
//                            Log.d("AAZ",user.getUsername());
//                            Log.d(TAG,ApiService.BASE_URL+"appbanhang/avatar/"+message);
                            username = user.getUsername();
                            urlAvatar = ApiService.BASE_URL+"appbanhang/avatar/"+message;
                            ApiService.apiService.updateAvatar(username,urlAvatar).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String message = response.body();
//                                    Log.d(TAG, message);
                                    if (message.equals("Fail")){
                                        Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Đổi ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                                        User user = DataLocalManager.getUser();
                                        User usernew = new User(user.getId(),user.getUsername(),user.getFullname(),user.getPassword(),message,user.getAddress(),user.getPhone());
                                        DataLocalManager.setUser(usernew);
                                        Glide.with(getApplicationContext()).load(usernew.getAvatar()).into(AccountFragment.imgAvatar);;
                                        finish();
                                    }
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.e("AAZ",t.getMessage());
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ThayAvatarActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void AnhXa() {
        imgChooseAvt = findViewById(R.id.image_choose_Avatar);
        btnChooseAvt = findViewById(R.id.button_choose_avatar);
        btnSelectAvt = findViewById(R.id.select_avatar);
        btnBack      = findViewById(R.id.back);
    }
}