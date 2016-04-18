package com.yliec.ewu.module.publish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yliec.ewu.R;
import com.yliec.ewu.api.entity.element.LocalImage;
import com.yliec.ewu.app.base.BaseActivity;
import com.yliec.lsword.compat.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PublishActivity extends BaseActivity {
//    @Bind(R.id.sdv_pub)
//    SimpleDraweeView mImageView;

    @Bind(R.id.et_title)
    EditText mEtTitle;

    @Bind(R.id.et_description)
    EditText mEtDescription;

    @Bind(R.id.ll_location)
    LinearLayout mLocationContainer;

    @Bind(R.id.tv_location)
    TextView mTvLocation;

    @Bind(R.id.et_price)
    EditText mEtPrice;

    @Bind(R.id.rl_category_container)
    LinearLayout mCategoryContainer;

    @Bind(R.id.tv_publish_category)
    TextView mTvCategory;

    @Bind(R.id.btn_publish)
    Button mBtnPublish;


    List<LocalImage> mUploadImages = new ArrayList<>();
    LocalImage mAddBtn = new LocalImage();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUploadImages.add(0, mAddBtn);
        Intent intent = getIntent();
        if (intent != null && intent.getParcelableArrayListExtra("images") != null) {
            List<LocalImage> imageList = intent.getParcelableArrayListExtra("images");
            L.d(TAG, "imageList: " + imageList);
            List<LocalImage> images = (List<LocalImage>) intent.getExtras().getSerializable("images");
            //得到需要上传的图片
            mUploadImages.addAll(images);
            if (images.size() > 0) {
                for (LocalImage image : images) {
                    L.d(TAG, Uri.parse("file://" + image.getImagePath()).toString());
                }
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_publish;
    }

    @Override
    protected void injectPrensenter() {

    }

    class UploadImagesAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mUploadImages.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 0 : 1;
        }

        class DeleteViewHolder extends RecyclerView.ViewHolder {

            public DeleteViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        class UploadImagesHolder extends RecyclerView.ViewHolder {

            public UploadImagesHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
