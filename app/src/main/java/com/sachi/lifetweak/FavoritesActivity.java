package com.sachi.lifetweak;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sachi.lifetweak.adapter.FavoriteTweakAdapter;
import com.sachi.lifetweak.db.LifeTweaksStore;
import com.sachi.lifetweak.fragment.FavoriteTweakFragment;
import com.sachi.lifetweak.fragment.VerticalDepthPageTransformer;
import com.sachi.lifetweak.model.LifeTweak;
import com.squareup.picasso.Picasso;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import io.realm.RealmResults;

/**
 * Created by jombay on 31/3/16.
 */
public class FavoritesActivity extends AppCompatActivity implements FavoriteTweakFragment.NotifyListner {

    FavoriteTweakAdapter lifeTweakAdapter;
    private ImageView imgNavdrawer;
    private ImageView imgTopPage;
    private Toolbar mToolBar;
    private Animation mSlideUp;
    private Animation mSlideDown;
    private VerticalViewPager verticalViewPager;
    private RealmResults<LifeTweak> lifeTweakArrayList;
    private RelativeLayout adapterNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        imgTopPage = (ImageView) findViewById(R.id.ic_top);
        mSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        mSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        adapterNull = (RelativeLayout) findViewById(R.id.txt_adapter_null);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("");
        imgNavdrawer = (ImageView) findViewById(R.id.img_nav_drawer);
        imgNavdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this, ActiityNavigation.class);
                startActivity(intent);

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getSupportActionBar().isShowing()) {
                    mToolBar.startAnimation(mSlideUp);
                    getSupportActionBar().hide();
                }
            }
        }, 3000);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);

        lifeTweakArrayList = readAllLifeTweaks();
        lifeTweakAdapter = new FavoriteTweakAdapter(getSupportFragmentManager(), lifeTweakArrayList);
        verticalViewPager.setAdapter(lifeTweakAdapter);
        if (lifeTweakAdapter.getCount() == 0) {
            adapterNull.setVisibility(View.VISIBLE);
        }
        verticalViewPager.setPageTransformer(true, new VerticalDepthPageTransformer());
        verticalViewPager.setOffscreenPageLimit(lifeTweakArrayList.size());
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Picasso.with(FavoritesActivity.this).load(R.drawable.ic_refresh_light).into(imgTopPage);
                } else {
                    Picasso.with(FavoritesActivity.this).load(R.drawable.ic_top_white).into(imgTopPage);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imgTopPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verticalViewPager.setCurrentItem(0);
            }
        });
    }

    private RealmResults<LifeTweak> readAllLifeTweaks() {
        RealmResults<LifeTweak> lifeTweakRealmResults;
        lifeTweakRealmResults = LifeTweaksStore.getInstance(this).getAllFavorites();
        return lifeTweakRealmResults;

    }

    public void onTap(View v) {
        if (getSupportActionBar().isShowing()) {
            mToolBar.startAnimation(mSlideUp);
            getSupportActionBar().hide();
        } else {
            mToolBar.startAnimation(mSlideDown);
            getSupportActionBar().show();

        }
    }

    @Override
    public void onFavoriteTweakUnliked() {
        lifeTweakAdapter.notifyDataSetChanged();
        verticalViewPager.destroyDrawingCache();
        if (lifeTweakAdapter.getCount() == 0) {

            adapterNull.setVisibility(View.VISIBLE);
        }
    }
}
