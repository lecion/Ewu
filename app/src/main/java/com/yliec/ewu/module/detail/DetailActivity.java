package com.yliec.ewu.module.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjj.Util;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yliec.ewu.R;
import com.yliec.ewu.api.entity.element.Goods;
import com.yliec.ewu.api.entity.element.Reply;
import com.yliec.ewu.app.base.BaseActivity;
import com.yliec.ewu.app.common.C;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(DetailPresenter.class)
public class DetailActivity extends BaseActivity<DetailPresenter> {
    private String mGoodsId;
    public static final String GOODS_ID = "goods_id";

    @Bind(R.id.iv_avatar)
    SimpleDraweeView mAvatar;

    @Bind(R.id.tv_username)
    TextView mTvUsername;

    @Bind(R.id.tv_time)
    TextView mTvTime;

    @Bind(R.id.tv_price)
    TextView mTvPrice;

    @Bind(R.id.tv_detail)
    TextView mTvDetail;

    @Bind(R.id.pb_load)
    ProgressBar mPbLoad;

    @Bind(R.id.ll_pictures_container)
    LinearLayout llContainer;

    @Bind(R.id.rv_reply)
    RecyclerView mRvReply;

    private List<Reply> mReplyList;

    private ReplyAdapter mReplyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent();
        initView();
        laodData();
    }

    private void initView() {
        mReplyList = new ArrayList<>();
        mRvReply.setLayoutManager(new LinearLayoutManager(this));
        mRvReply.setAdapter(mReplyAdapter = new ReplyAdapter());
    }

    private void laodData() {
        mPbLoad.setVisibility(View.VISIBLE);
        getPresenter().getGoods(mGoodsId);
    }

    public void onGoods(Goods goods) {
        mPbLoad.setVisibility(View.GONE);
        //TODO 显示商品详情
        mTvUsername.setText(goods.getUser().getName());

        mTvTime.setText(goods.getCreatedAt());

        mTvPrice.setText("￥" + goods.getPrice());

        mTvDetail.setText(goods.getDetail());

        addPictures(goods.getPictures());

        addReplies(goods.getReplies());
    }

    private void addReplies(List<Reply> replies) {
        mReplyList.addAll(replies);
        mReplyAdapter.notifyDataSetChanged();
    }

    private void addPictures(List<String> pictures) {
        llContainer.removeAllViews();
        for (String pic : pictures) {
            String url = C.QN_HOST + pic;
            llContainer.addView(getDraweeView(url));
        }
    }

    private View getDraweeView(String url) {
        SimpleDraweeView view = new SimpleDraweeView(this);
        view.setImageURI(Uri.parse(url));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(this, 600)));
        view.setPadding(8, 8, 8, 8);
        return view;
    }

    private void handleIntent() {
        Intent intent = getIntent();
        mGoodsId = intent.getStringExtra(GOODS_ID);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    protected void injectPrensenter() {
        getApp().getApiComponent().inject(getPresenter());
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, DetailActivity.class);
    }

    class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyHolder> {


        @Override
        public ReplyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ReplyHolder(LayoutInflater.from(DetailActivity.this).inflate(R.layout.item_goods_reply, parent, false));
        }

        @Override
        public void onBindViewHolder(ReplyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mReplyList.size();
        }

        class ReplyHolder extends RecyclerView.ViewHolder {

            public ReplyHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
