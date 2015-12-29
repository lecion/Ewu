package com.yliec.ewu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by Lecion on 11/18/15.
 */
public abstract class BaseActivity<PresenterType extends Presenter> extends NucleusAppCompatActivity<PresenterType> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectPrensenter();
        super.onCreate(savedInstanceState);
        if (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT != getRequestedOrientation()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(getContentView());

    }

    protected abstract int getContentView();

    protected abstract void injectPrensenter();


}
