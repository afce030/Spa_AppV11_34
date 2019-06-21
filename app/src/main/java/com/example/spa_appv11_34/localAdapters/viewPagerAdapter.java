package com.example.spa_appv11_34.localAdapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.spa_appv11_34.Fragmentos.searchPost;
import com.example.spa_appv11_34.Fragmentos.searchUsers;

public class viewPagerAdapter extends FragmentPagerAdapter {

    public viewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new searchPost();
            case 1:
                return new searchUsers();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:
                return "Publicaciones";
            case 1:
                return "Negocios";
        }
        return null;
    }
}
