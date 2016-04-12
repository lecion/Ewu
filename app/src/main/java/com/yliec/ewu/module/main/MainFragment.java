package com.yliec.ewu.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yliec.ewu.R;
import com.yliec.ewu.api.entity.element.Goods;
import com.yliec.ewu.app.base.BaseFragment;
import com.yliec.lsword.compat.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;


/**
 * A placeholder fragment containing a simple view.
 */
@RequiresPresenter(MainPresenter.class)
public class MainFragment extends BaseFragment<MainPresenter> {
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout mRefreshLayout;
    @Bind(R.id.rv_main)
    RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private List<Goods> mDatas;
    private LinearLayoutManager mLayoutManager;

    public MainFragment() {
        initData();
    }

    private void initData() {
        mDatas = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void injectPresenter() {
        final PresenterFactory<MainPresenter> presenterFactory = super.getPresenterFactory();
        setPresenterFactory(() -> {
            L.d(TAG, "presenterFactory " + presenterFactory);
            MainPresenter presenter = presenterFactory.createPresenter();
            getApiComponent().inject(presenter);
            return presenter;
        });
//        getApiComponent().inject(getPresenter());
    }

    @Override
    protected String getTitle() {
        return "测试";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter = new MainAdapter());
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getPresenter().refresh();
                mRefreshLayout.setLoadMore(true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                getPresenter().loadMore();
            }
        });
    }

    public void onChangeItems(List<Goods> goodsList, int pageIndex) {
        if (pageIndex == 1) {
            mDatas.clear();
            mDatas.addAll(goodsList);
            mAdapter.notifyDataSetChanged();
            mRefreshLayout.finishRefresh();
        } else {
            if (goodsList.size() > 0) {
                mDatas.addAll(goodsList);
                mAdapter.notifyDataSetChanged();
            } else {
                Snackbar.make(getView(), "没有更多商品拉", Snackbar.LENGTH_LONG).show();
                mRefreshLayout.setLoadMore(false);
            }
            mRefreshLayout.finishRefreshLoadMore();
        }
        L.d(TAG, "onChangeItems");
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
        @Override
        public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_main, parent, false));
        }

        @Override
        public void onBindViewHolder(MainHolder holder, int position) {
            holder.tv.setText(mDatas.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MainHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MainHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }
}
