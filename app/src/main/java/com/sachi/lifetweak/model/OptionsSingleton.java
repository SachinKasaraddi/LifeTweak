package com.sachi.lifetweak.model;

import com.sachi.lifetweak.R;

import java.util.ArrayList;

/**
 * Created by jombay on 30/3/16.
 */
public class OptionsSingleton {

    private static OptionsSingleton instance;

    private ArrayList<Options> optionsArrayList;

    public static OptionsSingleton getInstance() {
        if (instance == null) {
            instance = new OptionsSingleton();
        }
        return instance;
    }

    public ArrayList<Options> getOptionsArrayList() {
        return optionsArrayList;
    }

    private OptionsSingleton() {
        optionsArrayList = new ArrayList<>();
        optionsArrayList.add(new Options("Tweaks", R.drawable.ic_system_update_white_36dp));
        optionsArrayList.add(new Options("Favorites", R.drawable.ic_favorite_white_36dp));
        optionsArrayList.add(new Options("Invite your friends", R.drawable.ic_share));
        optionsArrayList.add(new Options("Rate", R.drawable.ic_star_rate_white_18dp));
        optionsArrayList.add(new Options("Help & Feedback", R.drawable.ic_help_white_36dp));

    }
}
