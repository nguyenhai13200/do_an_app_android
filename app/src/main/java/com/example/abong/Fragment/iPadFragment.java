package com.example.abong.Fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.abong.Listener.ClickAddiPadToCart;
import com.example.abong.R;
import com.example.abong.adapter.IpadAdapter;
import com.example.abong.modle.CartModle;
import com.example.abong.modle.Ipad;
import com.example.abong.ultil.AnimationCart;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link iPadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class iPadFragment extends Fragment {
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    IpadAdapter ipadAdapter;
    private ArrayList<Ipad> arrayIpad;
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

    public iPadFragment() {
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
    public static iPadFragment newInstance(String param1, String param2) {
        iPadFragment fragment = new iPadFragment();
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
        mView = inflater.inflate(R.layout.fragment_ipad, container, false);
        AnhXa();
        GetIpad();
        AddToCart();
        loadMoreData();
        return mView;
    }

    private void GetIpad() {
        ApiService.apiService.getIPad(page).enqueue(new Callback<ArrayList<Ipad>>() {
            @Override
            public void onResponse(Call<ArrayList<Ipad>> call, Response<ArrayList<Ipad>> response) {
                if (response != null & response.body().size() != 0){
                    for (int i = 0; i < response.body().size(); i++){
                        arrayIpad.add(response.body().get(i));
                    }
                    ipadAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(mainActivity, "Sản phẩm cuối cùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Ipad>> call, Throwable t) {
                Toast.makeText(mainActivity, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AddToCart() {
        //sự kiện add cart
        ipadAdapter.setOnClickAddiPadToCart(new ClickAddiPadToCart() {
            @Override
            public void onClickAddiPadToCart(ImageView imgAddCart, Ipad ipad) {
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
                                if (MainActivity.manggiohang.get(i).getTensanpham().equals(ipad.ten)){
                                    MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + sl);
                                    if (MainActivity.manggiohang.get(i).getSoluong() >= 10){
                                        MainActivity.manggiohang.get(i).setSoluong(10);
                                    }
                                    MainActivity.manggiohang.get(i).setGiasanpham(ipad.getGia() * MainActivity.manggiohang.get(i).getSoluong());
                                    exists = true;
                                }
                            }
                            if (exists == false){
                                int soluong = 1;
                                MainActivity.manggiohang.add(new CartModle(ipad.getId(),ipad.getTen(), ipad.getGia(), ipad.getHinhanh(), soluong));
                                MainActivity.setCountItemInCart(MainActivity.getCountt() + 1);
                            }
                        }else {
                            int soluong = 1;
                            MainActivity.manggiohang.add(new CartModle(ipad.getId(),ipad.getTen(), ipad.getGia(), ipad.getHinhanh(),soluong));
                            MainActivity.setCountItemInCart(MainActivity.getCountt() + 1);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }


    private void loadNextPage(){// load trang kế
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetIpad();
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
        recyclerView = mView.findViewById(R.id.RecyclerviewiPad);
        arrayIpad = new ArrayList<>();
        progressBar = mView.findViewById(R.id.ProgressBar);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ipadAdapter = new IpadAdapter(arrayIpad,getContext());
        recyclerView.setAdapter(ipadAdapter);
    }
}