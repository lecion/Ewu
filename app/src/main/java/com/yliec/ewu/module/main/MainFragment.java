package com.yliec.ewu.module.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cjj.Util;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yliec.ewu.R;
import com.yliec.ewu.api.entity.element.Goods;
import com.yliec.ewu.api.entity.element.Picture;
import com.yliec.ewu.app.base.BaseFragment;
import com.yliec.ewu.net.Api;
import com.yliec.ewu.widget.LDraweeView;
import com.yliec.lsword.compat.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    @Bind(R.id.fab_publish)
    FloatingActionButton mActionButton;
    private MainAdapter mAdapter;
    private List<Goods> mDatas;
    private LinearLayoutManager mLayoutManager;

    protected int sortType = Api.SORT_TYPE.POP;
    private static final int TAKE_PHOTO = 1;

    public MainFragment() {
        initData();
    }

    private void initData() {
        mDatas = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                getPresenter().refresh(sortType);
                mRefreshLayout.setLoadMore(true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                getPresenter().loadMore(sortType);
            }
        });
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 发布商品
                openCamera();
            }
        });
        lazyLoad();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d(TAG, "requestCode TAKE_PHOTO resultCode "
                + resultCode);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    //TODO 获得图片并存储
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    L.d(TAG, "Bitmap " + bitmap);
                }

                break;
        }
    }

    /**
     * 等待视图创建完毕后加载数据
     */
    private void lazyLoad() {
        mRefreshLayout.autoRefresh();
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

    public void onNetError(Throwable throwable) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishRefreshLoadMore();
        Snackbar.make(getView(), "网络错误，请检查网络连接", Snackbar.LENGTH_LONG).show();
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
        @Override
        public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_main, parent, false);
            return new MainHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MainHolder holder, int position) {
//            holder.mCardView
            Goods goods = mDatas.get(position);

            String userName;
            String avatar;
            try {
                userName = TextUtils.isEmpty(goods.getUser().getName()) ? "匿名" : goods.getUser().getName();
                avatar = TextUtils.isEmpty(goods.getUser().getAvatar()) ? "" : goods.getUser().getAvatar();
            } catch (NullPointerException e) {
                userName = "匿名";
                avatar = "";
            }

            if (!"".equals(avatar)) {
                holder.ivAvatar.setImageURI(Uri.parse(avatar));
            }

            handleImage(avatar);

            int dp8 = Util.dip2px(getContext(), 8);
            int dp120 = Util.dip2px(getContext(), 120);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp120, dp120);
            params.setMargins(dp8, dp8, 0, dp8);


            //有图
            if (goods.getPictures() != null && goods.getPictures().size() > 0) {
                holder.llContainer.removeAllViews();
                for (Picture pic : goods.getPictures()) {
                    LDraweeView draweeView = new LDraweeView(getContext());
                    draweeView.setDraweeViewUrl(pic.getUrl());
                    holder.llContainer.addView(draweeView, params);
                }
            } else {
                //没图
                Log.d(TAG, " pictures " + goods.getPictures());
                holder.llContainer.removeAllViews();
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.mipmap.avatar);
                holder.llContainer.addView(imageView, params);
            }


            holder.tvUserName.setText(userName);
            holder.tvPrice.setText("￥" + String.valueOf(goods.getPrice()));
            holder.tvDetail.setText(goods.getDetail());
        }

        private void handleImage(String imgUrl) {

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MainHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_avatar)
            SimpleDraweeView ivAvatar;
            @Bind(R.id.tv_username)
            TextView tvUserName;
            @Bind(R.id.iv_goods)
            ImageView ivGoods;
            @Bind(R.id.tv_price)
            TextView tvPrice;
            @Bind(R.id.tv_detail)
            TextView tvDetail;
            @Bind(R.id.ll_pictures_container)
            LinearLayout llContainer;

            public MainHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pop:
                sortType = Api.SORT_TYPE.POP;
                break;
            case R.id.time:
                sortType = Api.SORT_TYPE.TIME;
                break;
            case R.id.price:
                sortType = Api.SORT_TYPE.PRICE;
                break;
        }
        getPresenter().refresh(sortType);
        return super.onOptionsItemSelected(item);
    }
}
