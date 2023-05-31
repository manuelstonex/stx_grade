package com.van.sysmcu;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.van.jni.VanMcu;

public class MainActivity extends AppCompatActivity {
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(mPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Example of a call to a native method
        /*TextView tv = findViewById(R.id.sample_text);
        tv.setText(VanMcu.getVersion());*/
//        VanMcu.setOnDebugListener(mListener);
    }

    public void onClick(View view) {
        //TextView tv = findViewById(R.id.sample_text);
        //tv.setText(VanMcu.getVersion());
    }

    /*VanMcu.OnDebugListener mListener = new VanMcu.OnDebugListener() {
        @Override
        public void OnDebug(String str) {
            System.out.println("On Debug: " + str);
        }
    };*/

    class PagerAdapter extends FragmentPagerAdapter {
        private final Context mContext;
        private final int[] TAB_TITLES = new int[]{R.string.usual, R.string.can, R.string.storage, R.string.advanced, R.string.can_test};
        private final Fragment[] FRAGMENTS = new Fragment[]{new UsualFragment(), new CanFragment(),
                                                            new StorageFragment(), new AdvancedFragment(),
                                                            new CanTestFragment()};

        public PagerAdapter(Context context, FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return FRAGMENTS[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            return FRAGMENTS.length;
        }
    }
}
