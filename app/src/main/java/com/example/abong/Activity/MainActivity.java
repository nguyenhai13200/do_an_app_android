package com.example.abong.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.abong.R;
import com.example.abong.adapter.IphoneAdapter;
import com.example.abong.adapter.ViewPager2Adapter;
import com.example.abong.modle.CartModle;
import com.example.abong.modle.Iphone;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static int countt;
    private static NotificationBadge notificationBadge;
    public static ViewPager2 viewPager2;
    private ViewPager2Adapter adapter;
    private BottomNavigationView bottomNavigationView;
    private View viewEndAnimation;
    private ImageView viewAnimation,imgCart, imgSearch;
    public static ArrayList<CartModle> manggiohang;
    public static boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (manggiohang != null){

        }else {
            manggiohang = new ArrayList<>();
        }
        notificationBadge = findViewById(R.id.bagde);
        viewAnimation = findViewById(R.id.view_animation);
        viewEndAnimation = findViewById(R.id.view_end_animation);
        viewPager2 = findViewById(R.id.ViewPager2);
        bottomNavigationView = findViewById(R.id.BottomNavigation);
        adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_phone).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_mac).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_ipad).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.menu_user).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.menu_phone:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.menu_mac:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.menu_ipad:
                        viewPager2.setCurrentItem(3);
                        break;
                    case R.id.menu_user:
                        viewPager2.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
        //giỏ hàng
        imgCart = findViewById(R.id.cartButton);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GioHangActivity.class);
                startActivity(intent);
            }
        });
        //tìm kiếm
        imgSearch = findViewById(R.id.search_button);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TimKiemActivity.class);
                startActivity(intent);
            }
        });
    }
    public View getViewEndAnimation() {
        return viewEndAnimation;
    }

    public ImageView getViewAnimation() {
        return viewAnimation;
    }

    public static void setCountItemInCart(int count){
        countt = count;
        notificationBadge.setNumber(count);
    }
    public static int getCountt() {
        return countt;
    }
}