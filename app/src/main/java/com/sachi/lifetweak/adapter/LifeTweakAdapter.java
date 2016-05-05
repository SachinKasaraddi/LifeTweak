package com.sachi.lifetweak.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sachi.lifetweak.fragment.LifeTweakFragment;
import com.sachi.lifetweak.model.LifeTweak;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by jombay on 28/3/16.
 */
public class LifeTweakAdapter extends FragmentPagerAdapter {

    private RealmResults<LifeTweak> lifetweaks;

    public LifeTweakAdapter(FragmentManager fragmentManager, RealmResults<LifeTweak> lifetweaks) {
        super(fragmentManager);
        this.lifetweaks = lifetweaks;
    }

    @Override
    public Fragment getItem(int position) {
        return LifeTweakFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return this.lifetweaks.size();

    }


}
