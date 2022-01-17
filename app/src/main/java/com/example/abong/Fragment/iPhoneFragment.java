package com.example.abong.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abong.Activity.MainActivity;
import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.adapter.IphoneAdapter;
import com.example.abong.modle.CartModle;
import com.example.abong.modle.Iphone;
import com.example.abong.ultil.AnimationCart;
import com.example.abong.Listener.ClickAddIphoneToCart;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link iPhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class iPhoneFragment extends Fragment {
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    IphoneAdapter iphoneAdapter;
    private ArrayList<Iphone> arrayIphone;
    int page = 1;
    private ProgressBar progressBar;
    private boolean isLoading;
    private View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public iPhoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static iPhoneFragment newInstance(String param1, String param2) {
        iPhoneFragment fragment = new iPhoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_iphone, container, false);
        AnhXa();
        GetIphone();
//        GetDataIphone(page);
        AddToCart();
        loadMoreData();
        return mView;
    }

    private void GetIphone() {
        ApiService.apiService.getIPhone(page).enqueue(new Callback<ArrayList<Iphone>>() {
            @Override
            public void onResponse(Call<ArrayList<Iphone>> call, Response<ArrayList<Iphone>> response) {
                if (response != null && response.body().size() != 0){
                    for (int i = 0;i < response.body().size(); i++){
                        arrayIphone.add(response.body().get(i));
                    }
                    iphoneAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(mainActivity, "Sản phẩm cuối cùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Iphone>> call, Throwable t) {
                Toast.makeText(mainActivity, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddToCart() {
        //sự kiện add cart
        iphoneAdapter.setOnClickAddCart(new ClickAddIphoneToCart() {
            @Override
            public void onClickAddToCart(ImageView imgAddCart, Iphone iphone) {
                AnimationCart.translateAnimation(mainActivity.getViewAnimation(),imgAddCart,mainActivity.getViewEndAnimation(), new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (MainActivity.manggiohang.size() > 0){
                            int sl = 1;
                            boolean exists = false;
                            for (int i = 0; i < MainActivity.manggiohang.size(); i++){
                                if (MainActivity.manggiohang.get(i).getTensanpham().equals(iphone.ten)){
//                            Log.d("TAG",String.valueOf(MainActivity.manggiohang.get(i).getTensanpham()));
//                            Log.d("TAG",TenChitiet);
                                    MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + sl);
                                    if (MainActivity.manggiohang.get(i).getSoluong() >= 10){
                                        MainActivity.manggiohang.get(i).setSoluong(10);
                                    }
                                    MainActivity.manggiohang.get(i).setGiasanpham(iphone.getGia() * MainActivity.manggiohang.get(i).getSoluong());
                                    exists = true;
                                }
                            }
                            if (exists == false){
                                int soluong = 1;
                                MainActivity.manggiohang.add(new CartModle(iphone.getId(),iphone.getTen(), iphone.getGia(), iphone.getHinhanh(), soluong));
                                MainActivity.setCountItemInCart(MainActivity.getCountt() + 1);
                            }
                        }else {
                            int soluong = 1;
                            MainActivity.manggiohang.add(new CartModle(iphone.getId(),iphone.getTen(), iphone.getGia(), iphone.getHinhanh(),soluong));
                            MainActivity.setCountItemInCart(MainActivity.getCountt() + 1);
                        }
//                        iphone.setAddtoCart(true);
//                        imgAddCart.setBackgroundResource(R.drawable.bg_gray_cart);
//                        iphoneAdapter.notifyDataSetChanged();
//
//                        int soluong = 1;
//                        MainActivity.manggiohang.add(new CartModle(iphone.getId(),iphone.getTeniphone(), iphone.getGiaiphone(), iphone.getHinhanhiphone(), soluong));
//
//                        mainActivity.setCountItemInCart(mainActivity.getCountt()+1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }


//    private void GetDataIphone(int page) {
//            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//            String duongdan = Sever.urliPhone + String.valueOf(page);
//            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,duongdan,null,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            if(response != null && response.length() != 0){
////                                Log.d("AAA", "onResponse: "+response);
//                                progressBar.setVisibility(View.GONE);
////                                arrayDienthoai.clear();
//                                for(int i =0; i< response.length(); i++){
//                                    try {
//                                        JSONObject jsonObject = response.getJSONObject(i);
//                                        arrayIphone.add(new Sanpham(
//                                                jsonObject.getInt("id"),
//                                                jsonObject.getString("teniphone"),
//                                                jsonObject.getInt("giaiphone"),
//                                                jsonObject.getString("hinhanhiphone"),
//                                                jsonObject.getString("motaiphone")));
//                                        sanphamAdapter.notifyDataSetChanged();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }else{
//                                CheckConnection.ShowToast_Short(getContext(),"Sản phẩm cuối cùng!");
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    }
//            );
//            requestQueue.add(jsonArrayRequest);
//    }
    private void loadNextPage(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetIphone();
//                GetDataIphone(page);
                isLoading = false;
                progressBar.setVisibility(View.GONE);
            }
        },2000);
    }
    private void loadMoreData(){
        // sự kiện scroll load
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    isLoading = true;
                    progressBar.setVisibility(View.VISIBLE);
                    ++page;
                    loadNextPage();
                }
            }
        });
    }
    private void AnhXa() {
        mainActivity = (MainActivity) getActivity();
        recyclerView = mView.findViewById(R.id.RecyclerviewiPhone);
        arrayIphone = new ArrayList<>();
        progressBar = mView.findViewById(R.id.ProgressBar);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        iphoneAdapter = new IphoneAdapter(arrayIphone, getContext());
        recyclerView.setAdapter(iphoneAdapter);
    }
}