package com.yliec.ewu.app.base;

import android.os.Bundle;

import nucleus.presenter.RxPresenter;

/**
 * Created by Lecion on 4/17/16.
 */
public class BasePresenter<View> extends RxPresenter<View> {
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }
}
