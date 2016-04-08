package com.yliec.ewu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yliec.lsword.compat.util.L;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.rv_main)
    RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private List<String> mDatas;
    private LinearLayoutManager mLayoutManager;

    public MainFragment() {
        initData();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("item " + i);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter = new MainAdapter());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int mCurrentPage = 1;
            private boolean mLoading = true;
            private int mPreviousTotal = 0;
            private int lastVisible, totalCount;


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalCount = mLayoutManager.getItemCount();
                lastVisible = mLayoutManager.findLastVisibleItemPosition();
                L.d("totalCount " + totalCount + " lastVisible " + lastVisible);

                if (mLoading) {
                    if (totalCount > mPreviousTotal) {
                        mLoading = false;
                        mPreviousTotal = totalCount;
                    }
                }

                if (!mLoading && (lastVisible >= totalCount - 1)) {
                    mCurrentPage++;
                    onLoadMore(mCurrentPage);
                    mLoading = true;
                }

            }

            private void onLoadMore(int page) {
                Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .map(aLong -> {
                            ArrayList<String> more = new ArrayList<String>();
                            for (int i = 0; i < 10; i++) {
                                more.add("第" + page + "页 loadmore " + i);
                            }
                            mDatas.addAll(more);
                            mAdapter.notifyDataSetChanged();
                            return null;
                        }).subscribe();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        mRefreshLayout.setOnRefreshListener(this);
        return v;
    }

    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(aLong -> {
                    mDatas.add(0, "item新数据");
                    mRefreshLayout.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    return null;
                }).subscribe();
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
        @Override
        public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_main, parent, false));
        }

        @Override
        public void onBindViewHolder(MainHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
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
