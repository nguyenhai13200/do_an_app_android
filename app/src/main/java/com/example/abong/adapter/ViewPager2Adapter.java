package com.example.abong.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.abong.Fragment.AccountFragment;
import com.example.abong.Fragment.HomeFragment;
import com.example.abong.Fragment.MacFragment;
import com.example.abong.Fragment.iPadFragment;
import com.example.abong.Fragment.iPhoneFragment;

import org.jetbrains.annotations.NotNull;

public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new iPhoneFragment();
            case 2:
                return new MacFragment();
            case 3:
                return new iPadFragment();
            case 4:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
