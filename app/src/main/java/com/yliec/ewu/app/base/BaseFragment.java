package com.yliec.ewu.app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yliec.ewu.R;
import com.yliec.ewu.app.App;
import com.yliec.ewu.di.component.ApiComponent;
import com.yliec.lsword.compat.util.L;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusSupportFragment;

/**
 * Created by Lecion on 4/10/16.
 */
public abstract class BaseFragment<PresenterType extends Presenter> extends NucleusSupportFragment<PresenterType> {
    protected final String TAG = getClass().getSimpleName();
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.d(TAG, "onActivityCreated");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.d(TAG, "setUserVisibleHint " + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged " + hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d(TAG, "onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d(TAG, "onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.d(TAG, "onPause");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((Activity) context).setTitle(getTitle());
        L.d(TAG, "onAttatch");
    }

    @Override
    public void onCreate(Bundle bundle) {
        injectPresenter();
        super.onCreate(bundle);
        L.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (mToolbar != null) {
            mToolbar.setTitle(getTitle());
        }
        L.d(TAG, "onViewCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        L.d(TAG, "onDestroyView");
    }

    protected void injectPresenter() {
    }

    protected abstract String getTitle();

    protected ApiComponent getApiComponent() {
        return getApp().getApiComponent();
    }

    protected App getApp() {
        return (App)getActivity().getApplication();
    }

}
