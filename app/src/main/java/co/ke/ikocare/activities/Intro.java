package co.ke.ikocare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.ke.ikocare.R;
import co.ke.ikocare.utilities.PreferenceManager;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Intro extends AppCompatActivity {
    public PreferenceManager prefManager;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    private LinearLayout dotsLayout , separator,randLayouts,genLayout;
    private TextView[] dots;
    private TextView btAction, btnPrev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PreferenceManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_intro);
        viewPager = findViewById(R.id.welcome_viewpager);
        layouts = new int[]{
                R.layout.fragment_welcome_one,
                R.layout.fragment_welcome_two};
        changeStatusBarColor();
        dotsLayout = findViewById(R.id.layoutDots);
        randLayouts = findViewById(R.id.rand_layouts);
        genLayout = findViewById(R.id.gen_layout);
        addDots(0);
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        btAction = findViewById(R.id.bt_action);
        btnPrev = findViewById(R.id.bt_previous);
        btAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(-1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });


    }

    private void addDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(Intro.this, MainActivity.class));
        finish();
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btAction.setText(getString(R.string.finish));
                View root = genLayout.getRootView();
                btAction.setBackgroundColor(getResources().getColor(R.color.wel2bg));
                btnPrev.setBackgroundColor(getResources().getColor(R.color.wel2bg));
                dotsLayout.setBackgroundColor(getResources().getColor(R.color.wel2bg));
                randLayouts.setBackgroundColor(getResources().getColor(R.color.wel2bg));
                root.setBackgroundColor(getResources().getColor(R.color.wel2bg));
                btnPrev.setText(getString(R.string.previous));
                btAction.setVisibility(View.VISIBLE);
                btnPrev.setVisibility(View.VISIBLE);
            } else {

                if (position == layouts.length - 2){
                    btAction.setBackgroundColor(getResources().getColor(R.color.wel1bg));
                    btnPrev.setBackgroundColor(getResources().getColor(R.color.wel1bg));
                    dotsLayout.setBackgroundColor(getResources().getColor(R.color.wel1bg));
                    randLayouts.setBackgroundColor(getResources().getColor(R.color.wel1bg));
                    View root = genLayout.getRootView();
                    root.setBackgroundColor(getResources().getColor(R.color.wel1bg));
                }
//                if (position == layouts.length - 3){
//                    btAction.setBackgroundColor(getResources().getColor(R.color.wel1bg));
//                    btnPrev.setBackgroundColor(getResources().getColor(R.color.wel1bg));
//                    dotsLayout.setBackgroundColor(getResources().getColor(R.color.wel1bg));
//                    randLayouts.setBackgroundColor(getResources().getColor(R.color.wel1bg));
//                    View root = genLayout.getRootView();
//                    root.setBackgroundColor(getResources().getColor(R.color.wel1bg));
//                }
                // still pages are left
                btAction.setText(getString(R.string.next));
                btnPrev.setText(getString(R.string.previous));
                btAction.setVisibility(View.VISIBLE);
                btnPrev.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
