package com.example.kshitiz.aas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
private static int NUM_PAGES=3;
private ViewPager pager;
private PagerAdapter pa;
Button bs,bf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager=findViewById(R.id.displayfrag);
        pa=new OptionsAdapter(getSupportFragmentManager());
        pager.setAdapter(pa);
        bs=findViewById(R.id.student);
        bf=findViewById(R.id.faculty);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0){
                    bs.setVisibility(View.VISIBLE);
                    bf.setVisibility(View.VISIBLE);
                }else{
                    bs.setVisibility(View.GONE);
                    bf.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bs.setVisibility(View.GONE);
                bf.setVisibility(View.GONE);
                pager.setCurrentItem(1);
            }
        });
        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bs.setVisibility(View.GONE);
                bf.setVisibility(View.GONE);
                pager.setCurrentItem(2);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem()==0)
        super.onBackPressed();
        else
            pager.setCurrentItem(pager.getCurrentItem()-1);
    }

    private class OptionsAdapter extends FragmentStatePagerAdapter{

        public OptionsAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if(position==1)
                return new studentui();
            else if(position==2)
                return new facultyui();
            return new options();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }
}
