package com.yliec.ewu;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.yliec.lsword.compat.StatusBarCompat;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private MenuItem mPrevMenuItem;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        StatusBarCompat.compat(this);

        //Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        if (mDrawerLayout != null) {
            int color = getResources().getColor(R.color.primary_dark);
            mDrawerLayout.setStatusBarBackgroundColor(color);
        }

        //NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.nv_menu);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        //Container
        mContainer = (FrameLayout) findViewById(R.id.content);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        if (fab != null) {
//            fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", view1 -> {
//                    }).show()
//            );
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
                MainActivityFragment fragment = new MainActivityFragment();
                transaction.replace(mContainer.getId(),  fragment, "main");
                transaction.commit();
                fm.executePendingTransactions();

                break;
        }

        mDrawerLayout.closeDrawers();
        mPrevMenuItem = item;
        return true;
    }
}
