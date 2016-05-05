package com.sachi.lifetweak.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sachi.lifetweak.fragment.FavoriteTweakFragment;
import com.sachi.lifetweak.model.LifeTweak;

import io.realm.RealmResults;

/**
 * Created by jombay on 6/4/16.
 */
public class FavoriteTweakAdapter extends FragmentStatePagerAdapter {

    private RealmResults<LifeTweak> lifetweaks;

    public FavoriteTweakAdapter(FragmentManager fragmentManager, RealmResults<LifeTweak> lifetweaks) {
        super(fragmentManager);
        this.lifetweaks = lifetweaks;
    }


    @Override
    public Fragment getItem(int position) {
        return FavoriteTweakFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return this.lifetweaks.size();

    }
}

