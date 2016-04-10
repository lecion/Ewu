package com.yliec.ewu.app.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.yliec.ewu.R;
import com.yliec.lsword.compat.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by Lecion on 11/18/15.
 */
public abstract class BaseActivity<PresenterType extends Presenter> extends NucleusAppCompatActivity<PresenterType> {
    protected final String TAG = getClass().getSimpleName();
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectPrensenter();
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this);
        if (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT != getRequestedOrientation()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(getContentView());
        initToolbar();
    }

    protected void initToolbar() {
        if (mToolbar == null) {
            return;
        }

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (!TextUtils.isEmpty(NavUtils.getParentActivityName(this))) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);
            mToolbar.setNavigationOnClickListener(view -> finish());
        } else {
            getSupportActionBar().setHomeButtonEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    protected abstract int getContentView();

    protected abstract void injectPrensenter();


}
