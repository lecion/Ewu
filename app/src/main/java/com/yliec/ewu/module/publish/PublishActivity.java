package com.yliec.ewu.module.publish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yliec.ewu.R;
import com.yliec.ewu.api.entity.element.LocalImage;
import com.yliec.ewu.app.base.BaseActivity;
import com.yliec.lsword.compat.util.L;

import java.util.List;

public class PublishActivity extends BaseActivity {
//    @Bind(R.id.sdv_pub)
//    SimpleDraweeView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.getParcelableArrayListExtra("images") != null) {
            List<LocalImage> imageList = intent.getParcelableArrayListExtra("images");
            L.d(TAG, "imageList: " + imageList);
            List<LocalImage> images = (List<LocalImage>) intent.getExtras().getSerializable("images");
            if (images != null && images.size() > 0) {
                L.d(TAG, Uri.parse("file://" + images.get(0).getImagePath()).toString());
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


}
