package com.sachi.lifetweak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.sachi.lifetweak.adapter.LifeTweakAdapter;
import com.sachi.lifetweak.db.LifeTweaksStore;
import com.sachi.lifetweak.fragment.VerticalDepthPageTransformer;
import com.sachi.lifetweak.gcm.QuickstartPreferences;
import com.sachi.lifetweak.gcm.RegistrationIntentService;
import com.sachi.lifetweak.model.LifeTweak;
import com.sachi.lifetweak.model.LifeTweakSingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity{

    Context context = this;
    LifeTweakAdapter lifeTweakAdapter;
    private ArrayList<LifeTweak> testData;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    private Toolbar mToolBar;
    private Animation mSlideUp;
    private Animation mSlideDown;
    private ImageView imgNavdrawer;
    private ImageView imgTopPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        mSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        imgTopPage = (ImageView) findViewById(R.id.ic_top);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("");
        imgNavdrawer = (ImageView) findViewById(R.id.img_nav_drawer);
        imgNavdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActiityNavigation.class);
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

        final VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);

        if (verticalViewPager.getCurrentItem() == 0) {
            Picasso.with(context).load(R.drawable.ic_refresh_light).into(imgTopPage);
        }

        testData = new ArrayList<>();
        writeToDB();
        RealmResults<LifeTweak> lifeTweakArrayList = readAllLifeTweaks();
        lifeTweakAdapter = new LifeTweakAdapter(getSupportFragmentManager(), lifeTweakArrayList);
        verticalViewPager.setOffscreenPageLimit(lifeTweakArrayList.size());
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Picasso.with(context).load(R.drawable.ic_refresh_light).into(imgTopPage);
                } else {
                    Picasso.with(context).load(R.drawable.ic_top_white).into(imgTopPage);
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
        if (getIntent().getExtras() != null) {
            String message = getIntent().getExtras().getString("LifeHacks");
            if (message != null) {
                try {
                    JSONArray lifetweaks = new JSONArray(message);
                    for (int i = 0; i < lifetweaks.length(); i++) {
                        JSONObject tweaks = lifetweaks.getJSONObject(i);
                        String imgeUrl = tweaks.getString("imageurl");
                        String lifehacks = tweaks.getString("lifehacks");
                        testData.add(new LifeTweak(10 + i, imgeUrl, lifehacks, 0));
                    }
                    for (LifeTweak lifeHacks : testData) {
                        LifeTweaksStore.getInstance(this).writeMessage(lifeHacks);
                        Log.d("Writing to Database", lifeHacks.getImgUrl());
                        lifeTweakAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d("Success", getString(R.string.gcm_send_message));
                } else {
                    Log.d("Error", getString(R.string.token_error_message));
                }
            }
        };


        verticalViewPager.setAdapter(lifeTweakAdapter);
        verticalViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.pagemargin));
        verticalViewPager.setPageTransformer(true, new VerticalDepthPageTransformer());

        // Registering BroadcastReceiver
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent1 = new Intent(this, RegistrationIntentService.class);
            startService(intent1);
        }

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
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void writeToDB() {

        if (!LifeTweaksStore.getInstance(context).isLifeTweaksExist()) {
            for (LifeTweak lifeTweak : LifeTweakSingleton.getInstance().getLifeTweakArrayList()) {
                LifeTweaksStore.getInstance(context).writeMessage(lifeTweak);

            }
        }

    }

    private RealmResults<LifeTweak> readAllLifeTweaks() {
        RealmResults<LifeTweak> lifeTweakRealmResults;
        lifeTweakRealmResults = LifeTweaksStore.getInstance(context).getAllLifeTweaks();

        return lifeTweakRealmResults;

    }


    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


}

