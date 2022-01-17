package com.example.abong.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.abong.Api.ApiService;
import com.example.abong.R;
import com.example.abong.adapter.SanphamnewAdapter;
import com.example.abong.modle.SanphamNew;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewMain;
    private ViewFlipper viewFlipper;
    private ArrayList<SanphamNew> sanphamNews;
    private SanphamnewAdapter sanphamNewAdapter;
    private View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        AnhXa();
        ActionViewFliper();
        GetSanphamnew();
//        GetDataSanphammoinhat();
        return mView;
    }

    private void GetSanphamnew() {
        ApiService.apiService.getSanphamnew().enqueue(new Callback<ArrayList<SanphamNew>>() {
            @Override
            public void onResponse(Call<ArrayList<SanphamNew>> call, Response<ArrayList<SanphamNew>> response) {
                if (response != null){
                    for (int i = 0; i < response.body().size();i++){
                        sanphamNews.add(response.body().get(i));
                    }
                    sanphamNewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanphamNew>> call, Throwable t) {
                Log.d("AAC",t.getMessage());
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    private void GetDataSanphammoinhat() {
//            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.urlSanphammoinhat,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            if(response != null){
//                                mangSanpham.clear();
//                                for(int i =0; i< response.length(); i++){
//                                    try {
//                                        JSONObject jsonObject = response.getJSONObject(i);
//                                        mangSanpham.add(new Sanpham(
//                                                jsonObject.getInt("id"),
//                                                jsonObject.getString("tenspn"),
//                                                jsonObject.getInt("giaspn"),
//                                                jsonObject.getString("hinhanhspn"),
//                                                jsonObject.getString("motaspn")));
//                                        sanphamAdapter.notifyDataSetChanged();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
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

    private void ActionViewFliper() {
        ArrayList<String> hinhquangcao = new ArrayList<>();
        hinhquangcao.add("https://images.fpt.shop/unsafe/fit-in/800x300/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2021/9/16/637673921890335276_F_H1_800x300.png");
        hinhquangcao.add("https://fptshop.com.vn/uploads/originals/2021/7/14/637618513392424701_bo-suu-tap-hinh-anh-iphone-13-chi-tiet-chan-thuc-va-day-du-nhat-g.jpeg");
        hinhquangcao.add("https://surfacepro.vn/upload/2021/03/18/727201_3.jpg");
        hinhquangcao.add("https://sa.tinhte.vn/2018/11/4470066_cover_home_macbook_air_2018.jpg");
        hinhquangcao.add("http://hanoimoi.com.vn/Uploads/images/tuandiep/2021/09/15/HNMO_Ipadmini6_15.jpeg");
        for(int i = 0; i < hinhquangcao.size(); i++){
            ImageView imageView = new ImageView(getContext());
            Glide.with(this).load(hinhquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(5000);
            viewFlipper.setAutoStart(true);
            Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);
            Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_right);
            viewFlipper.setInAnimation(animation_slide_in);
            viewFlipper.setOutAnimation(animation_slide_out);
        }
    }
    private void AnhXa() {
        recyclerViewMain = mView.findViewById(R.id.RecyclerviewMain);
        viewFlipper = mView.findViewById(R.id.Viewflipper);
        sanphamNews = new ArrayList<>();
        sanphamNewAdapter = new SanphamnewAdapter(getContext(),sanphamNews);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewMain.setAdapter(sanphamNewAdapter);
    }
}