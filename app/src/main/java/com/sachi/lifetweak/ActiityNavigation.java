package com.sachi.lifetweak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sachi.lifetweak.adapter.NavigationOptionsAdapter;
import com.sachi.lifetweak.model.Options;
import com.sachi.lifetweak.model.OptionsSingleton;

import java.util.ArrayList;

/**
 * Created by jombay on 30/3/16.
 */
public class ActiityNavigation extends Activity {

    ArrayList<Options> optionsArrayList;
    ListView listView;
    NavigationOptionsAdapter mAdapter;
    private ImageView imgClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nav_drawer);
        optionsArrayList = OptionsSingleton.getInstance().getOptionsArrayList();
        mAdapter = new NavigationOptionsAdapter(optionsArrayList, this);
        listView = (ListView) findViewById(R.id.list_options);
        imgClose = (ImageView) findViewById(R.id.ic_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    Intent intent = new Intent(ActiityNavigation.this, MainActivity.class);
                    startActivity(intent);
                } else if (i == 1) {
                    Intent intent = new Intent(ActiityNavigation.this, FavoritesActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

}
