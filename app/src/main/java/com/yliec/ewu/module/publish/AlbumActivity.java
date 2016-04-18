package com.yliec.ewu.module.publish;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.yliec.ewu.model.LocalImageModel;
import com.yliec.lsword.compat.util.L;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(AlbumPresenter.class)
public class AlbumActivity extends BaseActivity<AlbumPresenter> implements View.OnClickListener {
    public static final String PERMISSION_READ_EXTERNAL = "android.permission.READ_EXTERNAL_STORAGE";
    public static final int REQUEST_READ_EXTERNAL_CODE = 1;
    @Bind(R.id.rv_album)
    RecyclerView mRecyclerView;

    @Bind(R.id.btn_finish)
    Button mFinishButton;

    private File imageFile;

    RecyclerView.LayoutManager mGridLayoutManager;

    AlbumAdapter mAlbumAdapter;

    private List<LocalImage> mPhotos = new ArrayList<>();

    private static final int TAKE_PHOTO = 1;
    private boolean once = true;
    private boolean fromPublish = false;
    private int selectedSize = 0;
    public ArrayList<LocalImage> mSelectedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra(PublishActivity.UPLOADED_COUNT, 0) != 0) {
            selectedSize = intent.getIntExtra(PublishActivity.UPLOADED_COUNT, 0);
            fromPublish = true;
            L.d(TAG, "onCreate selectedSize: " + selectedSize);
        }
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAlbumAdapter = new AlbumAdapter(selectedSize));
        mFinishButton.setOnClickListener(this);
        mPhotos.add(0, new LocalImage());
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
            if (once) {
                getPresenter().loadPhotos();
                L.d(TAG, "loadPhotos");
                once = false;
            }
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
        getApiComponent().inject(getPresenter());
    }


    public void onItemChange(List<LocalImage> imageList) {
        mPhotos.clear();
        mPhotos.add(0, new LocalImage());
        mPhotos.addAll(imageList);
        L.d(TAG, "onItemChange" + imageList.size());
        mAlbumAdapter.notifyDataSetChanged();
    }

    public void onNetError(Throwable throwable) {
        Snackbar.make(getWindow().getDecorView(), "网络错误，请检查网络连接", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_finish) {
            if (fromPublish) {
                Intent intent = new Intent();
                ArrayList<LocalImage> addImages = new ArrayList<>();
                addImages.addAll(mSelectedList);
                intent.putParcelableArrayListExtra("addImages", addImages);
                setResult(RESULT_OK, intent);
                this.finish();
            } else {
                if (mSelectedList.size() > 0) {
                    Intent intent = new Intent(this, PublishActivity.class);
                    ArrayList<LocalImage> imageList = new ArrayList<>();
                    imageList.addAll(mSelectedList);
                    intent.putParcelableArrayListExtra("images", imageList);
                    startActivity(intent);
                    this.finish();
                } else {
                    Toast.makeText(AlbumActivity.this, "请选择至少一张图片", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> mSelectedIds = new ArrayList<>();
        private int MAX_SELECTED;

        private int mFloatColor = getResources().getColor(R.color.md_grey_A800);

        public AlbumAdapter(int selectedSize) {
            MAX_SELECTED = AlbumHelper.MAX_SELECT - selectedSize;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 0:
                    View v = LayoutInflater.from(AlbumActivity.this).inflate(R.layout.item_activity_album_camera, parent, false);
                    return new CameraHolder(v);
                default:
                    return new AlbumHolder(LayoutInflater.from(AlbumActivity.this).inflate(R.layout.item_activity_album, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) != 0) {
                AlbumHolder h = (AlbumHolder) holder;
                Uri uri = Uri.parse("file://" + mPhotos.get(position).getImagePath());
                int width = Util.dip2px(AlbumActivity.this, 130);
                int height = Util.dip2px(AlbumActivity.this, 130);
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setResizeOptions(new ResizeOptions(width, height))
                        .build();
                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setOldController(h.mDraweeView.getController())
                        .setImageRequest(request)
                        .build();
                h.mDraweeView.setController(controller);
                //设置tag，用于判断选中的状态
                h.mSelector.setTag(mPhotos.get(position).getImageId());
                if (mSelectedIds != null) {
                    boolean selected = mSelectedIds.contains(mPhotos.get(position).getImageId());
                    h.mSelector.setChecked(selected);
                    h.mFloatView.setBackgroundColor(selected ? mFloatColor : Color.TRANSPARENT);
                } else {
                    h.mSelector.setChecked(false);
                    h.mFloatView.setBackgroundColor(Color.TRANSPARENT);
                }

                h.mSelector.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        if (!mSelectedIds.contains(h.mSelector.getTag())) {
                            if (mSelectedIds.size() >= MAX_SELECTED) {
                                Toast.makeText(AlbumActivity.this, "最多只能选择" + MAX_SELECTED + "张图哦~", Toast.LENGTH_SHORT).show();
                                h.mSelector.setChecked(false);
                                return;
                            }
                            h.mFloatView.setBackgroundColor(mFloatColor);
                            mSelectedList.add(mPhotos.get(position));
                            mSelectedIds.add(mPhotos.get(position).getImageId());
                            mFinishButton.setText("完成(" + mSelectedIds.size() + ")");
                        }
                    } else {
                        if (mSelectedIds.contains(h.mSelector.getTag())) {
                            h.mFloatView.setBackgroundColor(Color.TRANSPARENT);
                            mSelectedList.remove(mPhotos.get(position));
                            mSelectedIds.remove(mPhotos.get(position).getImageId());
                            String btnStr = mSelectedIds.size() == 0 ? "选择图片" : "完成(" + mSelectedIds.size() + ")";
                            mFinishButton.setText(btnStr);
                        }
                    }
                });

                L.d(TAG, "mSelectedIds " + mSelectedIds.toString() + " mSelectedList " + mSelectedList);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 0 : 1;
        }

        @Override
        public int getItemCount() {
            return mPhotos.size();
        }

        class AlbumHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.sdv_photo)
            SimpleDraweeView mDraweeView;
            @Bind(R.id.tb_select)
            ToggleButton mSelector;
            @Bind(R.id.float_view)
            View mFloatView;

            public AlbumHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        class CameraHolder extends RecyclerView.ViewHolder {

            public CameraHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(v -> openCamera());
            }
        }
    }

    public void injectModle(LocalImageModel localImageModel) {
        getAppComponent().inject(localImageModel);
    }


    private void openCamera() {
        if (initImageFile()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(intent, TAKE_PHOTO);
        }
    }

    private boolean initImageFile() {
        if (hasSDCard()) {
            String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                    + File.separator
                    + "Ewu"
                    + File.separator;
            boolean hasDir = true;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                hasDir = dir.mkdirs();
            }
            if (hasDir) {
                String imgPath = dirPath + File.separator + System.currentTimeMillis() + ".png";
                imageFile = new File(imgPath);
                if (!imageFile.exists()) {
                    try {
                        imageFile.createNewFile();
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }

        }
        return false;
    }

    private boolean hasSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d(TAG, "requestCode TAKE_PHOTO resultCode "
                + resultCode);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (imageFile.exists()) {
                        LocalImage image = new LocalImage();
                        image.setImagePath(imageFile.getPath());
                        ArrayList<LocalImage> imageList = new ArrayList<>();
                        imageList.add(image);
                        if (fromPublish) {
                            Intent intent = new Intent();
                            intent.putParcelableArrayListExtra("addImages", imageList);
                            setResult(RESULT_OK, intent);
                            this.finish();

                        } else {
                            Intent intent = new Intent(this, PublishActivity.class);
                            intent.putParcelableArrayListExtra("images", imageList);
                            startActivity(intent);
                            this.finish();
                        }


                    }
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSelectedList.clear();
    }
}
