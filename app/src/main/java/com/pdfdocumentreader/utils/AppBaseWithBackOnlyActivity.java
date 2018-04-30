package com.pdfdocumentreader.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pdfdocumentreader.R;

import butterknife.ButterKnife;

/**
 * Created by mayur.p on 29/4/2018.
 */

public abstract class AppBaseWithBackOnlyActivity extends AppCompatActivity {
    // ****NOT USED IN****
    //UserTrackApplicationActivity, UserTrackAllApplicationListActivity, Login, Dashboard, UserLoginDashboard,
    // WebViewActivity, ServiceWebPages


    protected abstract AppBaseSetGet getActivityData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityData().getLayoutResId());
        ButterKnife.bind(this);
        initialiseToolbar();
    }

    void initialiseToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getActivityData().getToolbarTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
