package com.yliec.ewu.module.publish;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.Util;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yliec.ewu.R;
import com.yliec.ewu.api.entity.element.LocalImage;
import com.yliec.ewu.app.base.BaseActivity;
import com.yliec.lsword.compat.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AlbumPresenter.class)
public class AlbumActivity extends BaseActivity<AlbumPresenter> {
    public static final String PERMISSION_READ_EXTERNAL = "android.permission.READ_EXTERNAL_STORAGE";
    public static final int REQUEST_READ_EXTERNAL_CODE = 1;
    @Bind(R.id.rv_album)
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mGridLayoutManager;

    AlbumAdapter mAlbumAdapter;

    private List<LocalImage> mPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAlbumAdapter = new AlbumAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermiisions();
    }

    private void checkPermiisions() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION_READ_EXTERNAL) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_READ_EXTERNAL)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION_READ_EXTERNAL}, REQUEST_READ_EXTERNAL_CODE);
            }
        } else {
            L.d(TAG, "loadPhotos");
            getPresenter().loadPhotos();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPresenter().loadPhotos();
                } else {
                    Snackbar.make(getWindow().getDecorView(), "没有读取权限", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_album;
    }

    @Override
    protected void injectPrensenter() {
//        final PresenterFactory<AlbumPresenter> presenterFactory = super.getPresenterFactory();
//        setPresenterFactory(() -> {
//            L.d(TAG, "presenterFactory " + presenterFactory);
//            AlbumPresenter presenter = presenterFactory.createPresenter();
//            getAppComponent().inject(presenter);
//            return presenter;
//        });
        getAppComponent().inject(getPresenter());
    }

    public void onItemChange(List<LocalImage> imageList) {
        mPhotos.clear();
        mPhotos.addAll(imageList);
        L.d(TAG, "onItemChange" + imageList.size());
        mAlbumAdapter.notifyDataSetChanged();
    }

    class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {

        @Override
        public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(AlbumActivity.this).inflate(R.layout.item_activity_album, parent, false);
            return new AlbumHolder(v);
        }

        @Override
        public void onBindViewHolder(AlbumHolder holder, int position) {
//            holder.mDraweeView.setImageResource(R.mipmap.avatar);
//            holder.mImageView.setImageResource(R.mipmap.avatar);
            L.d(TAG, "start onBindViewHolder");
            Uri uri = Uri.parse("file://" + mPhotos.get(position).getImagePath());
            int width = Util.dip2px(AlbumActivity.this, 130);
            int height = Util.dip2px(AlbumActivity.this, 130);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setOldController(holder.mDraweeView.getController())
                    .setImageRequest(request)
                    .build();
            holder.mDraweeView.setController(controller);
//            holder.mDraweeView.setImageURI(uri);
            L.d(TAG, "onBindViewHolder" + uri.toString());
        }


        @Override
        public int getItemCount() {
            return mPhotos.size();
        }

        class AlbumHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.sdv_photo)
            SimpleDraweeView mDraweeView;
//            @Bind(R.id.iv_photo)
//            ImageView mImageView;

            public AlbumHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
