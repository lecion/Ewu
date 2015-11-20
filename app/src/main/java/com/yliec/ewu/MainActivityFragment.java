package com.yliec.ewu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private List<String> mDatas;

    public MainActivityFragment() {
        initData();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDatas.add("item " + i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter = new MainAdapter());
        return v;
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
