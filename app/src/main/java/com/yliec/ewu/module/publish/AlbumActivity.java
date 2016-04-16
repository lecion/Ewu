package com.yliec.ewu.module.publish;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yliec.ewu.R;
import com.yliec.ewu.api.entity.element.Photo;
import com.yliec.ewu.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AlbumPresenter.class)
public class AlbumActivity extends BaseActivity {
    @Bind(R.id.rv_album)
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mGridLayoutManager;

    AlbumAdapter mAlbumAdapter;

    private List<Photo> mPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAlbumAdapter = new AlbumAdapter());
    }

    private void initData() {
        mPhotos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Photo photo = new Photo();
            photo.setPath("http://aaa/aa/a/a" + i + ".jpg");
            mPhotos.add(photo);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_album;
    }

    @Override
    protected void injectPrensenter() {

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
            holder.mImageView.setImageResource(R.mipmap.avatar);
        }


        @Override
        public int getItemCount() {
            return mPhotos.size();
        }

        class AlbumHolder extends RecyclerView.ViewHolder {
            //            @Bind(R.id.sdv_photo)
//            SimpleDraweeView mDraweeView;
            @Bind(R.id.iv_photo)
            ImageView mImageView;

            public AlbumHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
