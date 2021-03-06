package com.yliec.ewu.module.publish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.utils.StringUtils;
import com.yliec.ewu.R;
import com.yliec.ewu.api.entity.element.LocalImage;
import com.yliec.ewu.api.entity.element.PostGoods;
import com.yliec.ewu.app.base.BaseActivity;
import com.yliec.ewu.module.detail.DetailActivity;
import com.yliec.ewu.net.QN;
import com.yliec.lsword.compat.util.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(PublishPresenter.class)
public class PublishActivity extends BaseActivity<PublishPresenter> implements View.OnClickListener {
    public static final int REQUEST_ADD_IMAGE = 1;
    public static final String UPLOADED_COUNT = "uploadedCount";

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
    RelativeLayout mCategoryContainer;

    @Bind(R.id.tv_publish_category)
    TextView mTvCategory;

    @Bind(R.id.btn_publish)
    Button mBtnPublish;

    @Bind(R.id.rv_upload_image)
    RecyclerView mRvUpload;

    UploadImagesAdapter mUploadImagesAdapter;


    ArrayList<LocalImage> mUploadImages = new ArrayList<>();

    //已经上传到七牛的映射，key为mUploadImages原始文件名，value为七牛返回的hash
    HashMap<String, String> mUploadedImages = new HashMap<>();
    LocalImage mAddBtn = new LocalImage();

    QN mQN = new QN();

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
                    mQN.upload(image.getImagePath(), null, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                L.d(TAG, " upload complete key: " + key + "  response " + response + "\n info: " + info);
                                try {
                                    mUploadedImages.put(image.getImagePath(), response.getString("hash"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        }
        initView();
    }

    private void initView() {
        mRvUpload.setLayoutManager(new GridLayoutManager(this, 4));
        mRvUpload.setAdapter(mUploadImagesAdapter = new UploadImagesAdapter());
        mBtnPublish.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_publish;
    }

    @Override
    protected void injectPrensenter() {
        getApiComponent().inject(getPresenter());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publish:
                boolean validate = true;
                String title = mEtTitle.getText().toString().trim();
                String description = mEtDescription.getText().toString().trim();
                String price = mEtPrice.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    mEtTitle.setError("标题不能为空");
                    validate = false;
                }
                if (TextUtils.isEmpty(description)) {
                    mEtDescription.setError("描述不能为空");
                    validate = false;
                }
                if (TextUtils.isEmpty(price)) {
                    mEtPrice.setError("请填写价格");
                    validate = false;
                }

                if (mUploadImages.size() <= 1) {
                    Toast.makeText(this, "请选择最少一张图片", Toast.LENGTH_LONG).show();
                    validate = false;
                }
                //TODO 验证所有字段
                if (validate) {
                    PostGoods goods = new PostGoods();
                    goods.setName(title);
                    goods.setDetail(description);
                    goods.setPrice(Float.parseFloat(price));
                    //TODO 获取分类列表
                    goods.setCategory("56f67b256dd167b26ff9b524");
                    ArrayList<String> pictures = new ArrayList<>();
                    for (LocalImage image : mUploadImages) {
                        String url = mUploadedImages.get(image.getImagePath());
                        if (!StringUtils.isNullOrEmpty(url)) {
                            pictures.add(url);
                        }

                    }
                    goods.setPictures(pictures);

                    getPresenter().publish(goods);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADD_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList addImages = data.getParcelableArrayListExtra("addImages");
                    if (addImages != null) {
                        mUploadImages.addAll(addImages);
                        mUploadImagesAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    public void showErrMsg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void toDetailActivity(String id) {
        Intent i = DetailActivity.getCallingIntent(this);
        i.putExtra(DetailActivity.GOODS_ID, id);
        startActivity(i);
        finish();
    }

    class UploadImagesAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new AddViewHolder(getLayoutInflater().inflate(R.layout.item_activity_publish_add, parent, false));
            } else {
                return new UploadImagesHolder(getLayoutInflater().inflate(R.layout.item_activity_publish_upload, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position != 0) {
                UploadImagesHolder h = (UploadImagesHolder) holder;
                h.mDraweeView.setImageURI(Uri.parse("file://" + mUploadImages.get(position).getImagePath()));
                h.mDelete.setOnClickListener(v -> {
                    mUploadImages.remove(position);
                    notifyDataSetChanged();
                });
            }
        }

        @Override
        public int getItemCount() {
            return mUploadImages.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 0 : 1;
        }

        class AddViewHolder extends RecyclerView.ViewHolder {

            public AddViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(view -> {
                    if (mUploadImages.size() >= 9) {
                        Toast.makeText(PublishActivity.this, "最多只能选9张图哦", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(PublishActivity.this, AlbumActivity.class);
                        int count = mUploadImages.size() - 1;
                        i.putExtra(UPLOADED_COUNT, count);
                        startActivityForResult(i, REQUEST_ADD_IMAGE);
                    }
                });
            }
        }

        class UploadImagesHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.sdv_photo)
            SimpleDraweeView mDraweeView;
            @Bind(R.id.ib_delete)
            ImageButton mDelete;

            public UploadImagesHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
