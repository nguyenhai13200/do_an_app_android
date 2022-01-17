package com.example.abong.Api;

import com.example.abong.modle.DonHang;
import com.example.abong.modle.ImageChitiet;
import com.example.abong.modle.Ipad;
import com.example.abong.modle.Iphone;
import com.example.abong.modle.Mac;
import com.example.abong.modle.SanPhamSearch;
import com.example.abong.modle.SanphamNew;
import com.example.abong.modle.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL = "http://192.168.1.132/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient()
                    .create();

    ApiService apiService = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build()
                                .create(ApiService.class);

    @GET("appbanhang/get_san_pham_moi_nhat.php")
    Call<ArrayList<SanphamNew>> getSanphamnew();

    @GET("appbanhang/getiphone.php")
    Call<ArrayList<Iphone>> getIPhone(@Query("page") int page);

    @GET("appbanhang/getmac.php")
    Call<ArrayList<Mac>> getMac(@Query("page") int page);

    @GET("appbanhang/getipad.php")
    Call<ArrayList<Ipad>> getIPad(@Query("page") int page);

    @GET("appbanhang/hinhanhiphone.php")
    Call<ArrayList<ImageChitiet>> getImageChitietIP(@Query("idIP") int idIP);

    @GET("appbanhang/hinhanhmac.php")
    Call<ArrayList<ImageChitiet>> getImageChitietMac(@Query("idMac") int idMac);

    @GET("appbanhang/hinhanhipad.php")
    Call<ArrayList<ImageChitiet>> getImageChitietIpad(@Query("idIpad") int idIpad);

    @GET("appbanhang/hinhanhspn.php")
    Call<ArrayList<ImageChitiet>> getImageChitietSpn(@Query("tenspn") String tenspn);

    @Multipart
    @POST("appbanhang/upload_avatar.php")
    Call<String> uploadAvatar(@Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("appbanhang/dangky.php")
    Call<String> dangkyUser(@Field("username") String taikhoan,
                            @Field("fullname") String hovaten,
                            @Field("password") String matkhau,
                            @Field("phonenumber") String phone);

    @FormUrlEncoded
    @POST("appbanhang/dangnhap.php")
    Call<ArrayList<User>> dangnhapUser(@Field("username") String taikhoan,
                                        @Field("password") String matkhau);

    @FormUrlEncoded
    @POST("appbanhang/capnhat_avatar.php")
    Call<String> updateAvatar(@Field("username") String taikhoan,
                              @Field("avatar") String avatar);

    @FormUrlEncoded
    @POST("appbanhang/doimatkhau.php")
    Call<String> doimatkhau(@Field("username") String taikhoan,
                            @Field("password") String matkhau,
                            @Field("passwordnew") String matkhaumoi);

    @FormUrlEncoded
    @POST("appbanhang/sua_diachi_nguoinhan.php")
    Call<String> suadiachi(@Field("username") String taikhoan,
                           @Field("fullname") String hovaten,
                            @Field("phone") String sodienthoai,
                            @Field("address") String diachi);

    @FormUrlEncoded
    @POST("appbanhang/gui_donhang.php")
    Call<String> insertDonhang(@Field("iduser") int iduser,
                           @Field("tongtien") long tongtien,
                           @Field("hinhthuc") String hinhthuc,
                           @Field("chitiet") String chitiet);

    @FormUrlEncoded
    @POST("appbanhang/list_donhang.php")
    Call<ArrayList<DonHang>> listDonhang(@Field("iduser") int iduser);

    @FormUrlEncoded
    @POST("appbanhang/timkiem.php")
    Call<ArrayList<SanPhamSearch>> search(@Field("search") String search);
}
