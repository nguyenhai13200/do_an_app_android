package com.example.abong.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.abong.Activity.DangkyActivity;
import com.example.abong.Activity.DangnhapActivity;
import com.example.abong.Activity.DoimatkhauActivity;
import com.example.abong.Activity.DonHangActivity;
import com.example.abong.Activity.MainActivity;
import com.example.abong.Activity.MapsActivity;
import com.example.abong.Activity.ThayAvatarActivity;
import com.example.abong.Activity.ThongTinActivity;
import com.example.abong.R;
import com.example.abong.data_local.DataLocalManager;
import com.example.abong.modle.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    public static ImageView btnChonAvatar, imgAvatar;
    private TextView username;
    private LinearLayout btnLogin, btnRegister, btnLogout, btnSetPass,btnThongTin, btnDonHang, btnDiaChi;
    private View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
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
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        AnhXa();
        EvnetButton();
        initLogin();
        return mView;
    }

    private void initLogin() {
        if (MainActivity.isLogin == true){
            btnLogin.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
            btnChonAvatar.setVisibility(View.VISIBLE);

            User user = DataLocalManager.getUser();
            username.setText(user.getUsername());
            Glide.with(getContext()).load(user.getAvatar()).placeholder(R.drawable.avatar_user).error(R.drawable.avatar_user).into(imgAvatar);

            btnThongTin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ThongTinActivity.class);
                    startActivity(intent);
                }
            });
            btnSetPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DoimatkhauActivity.class);
                    startActivity(intent);
                }
            });

            btnDonHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DonHangActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
        }
    }

    private void EvnetButton() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangnhapActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangkyActivity.class);
                startActivity(intent);
            }
        });

        btnChonAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThayAvatarActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataLocalManager.clearUser();
                MainActivity.isLogin = false;
                MainActivity.viewPager2.setCurrentItem(0);
                Toast.makeText(getContext(), "Đã đăng xuất tài khoản", Toast.LENGTH_SHORT).show();
            }
        });
        btnDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btnLogin = mView.findViewById(R.id.btn_login);
        btnRegister = mView.findViewById(R.id.btn_register);
        btnChonAvatar = mView.findViewById(R.id.btn_choosephoto);
        imgAvatar = mView.findViewById(R.id.avatar_username);
        btnLogout = mView.findViewById(R.id.btn_logout);
        username = mView.findViewById(R.id.textview_username);
        btnSetPass = mView.findViewById(R.id.btn_setpassword);
        btnDonHang = mView.findViewById(R.id.btn_donhang);
        btnDiaChi = mView.findViewById(R.id.dia_chi_shop);
        btnThongTin = mView.findViewById(R.id.btn_thongtin);
    }
}