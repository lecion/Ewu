package com.yliec.ewu.app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yliec.ewu.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusSupportFragment;

/**
 * Created by Lecion on 4/10/16.
 */
public abstract class BaseFragment<PresenterType extends Presenter> extends NucleusSupportFragment<PresenterType> {

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((Activity) context).setTitle(getTitle());
    }

    @Override
    public void onCreate(Bundle bundle) {
        injectPresenter();
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (mToolbar != null) {
            mToolbar.setTitle(getTitle());
        }
    }

    protected abstract void injectPresenter();

    protected abstract String getTitle();

}
