package com.yliec.ewu.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yliec.ewu.R;
import com.yliec.ewu.app.App;
import com.yliec.ewu.app.base.BaseActivity;
import com.yliec.ewu.module.login.LoginActivity;
import com.yliec.ewu.net.QN;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nv_menu)
    NavigationView mNavigationView;
    @Bind(R.id.content)
    FrameLayout mContainer;
    private MenuItem mPrevMenuItem;
    View mHeaderView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Drawer
        if (mDrawerLayout != null) {
            int color = getResources().getColor(R.color.primary_dark);
            mDrawerLayout.setStatusBarBackgroundColor(color);
        }

        //NavigationView
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
            mHeaderView = mNavigationView.getHeaderView(0);
            mHeaderView.setOnClickListener(this);
        }

        new QN().upload();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectPrensenter() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (mPrevMenuItem != null) {
            mPrevMenuItem.setChecked(false);
        }
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.main:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                MainFragment fragment = new MainFragment();
//                transaction.replace(mContainer.getId(),  fragment, "main");
                transaction.add(mContainer.getId(), fragment, "main");
                transaction.hide(fragment);
                transaction.show(fragment);
                transaction.commit();
                fm.executePendingTransactions();
                break;
        }

        mDrawerLayout.closeDrawers();
        mPrevMenuItem = item;
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == mHeaderView) {
            if (((App) getApplication()).isLogin()) {
                Toast.makeText(this, "个人信息", Toast.LENGTH_LONG).show();
            } else {
                Intent i = LoginActivity.getCallingIntent(this);
                startActivity(i);
            }
        }
    }
}
