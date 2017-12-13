package com.hothouse.encripttool.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hothouse.encripttool.R;
import com.hothouse.encripttool.adapter.MainFragmentPagerAdapter;
import com.hothouse.encripttool.ui.fragment.AESFragment;
import com.hothouse.encripttool.ui.fragment.RSAFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mainTab;
    private ViewPager mainVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainTab = findViewById(R.id.main_tab);
        mainVP = findViewById(R.id.main_vp);

        List<String> titleList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        titleList.add("AES");
        fragmentList.add(AESFragment.newInstance());

        titleList.add("RSA");
        fragmentList.add(RSAFragment.newInstance());

        mainVP.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(),titleList,fragmentList));
        mainTab.setupWithViewPager(mainVP);

    }
}
