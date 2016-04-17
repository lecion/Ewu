package com.yliec.ewu.module.publish;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yliec.ewu.api.entity.element.LocalImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lecion on 4/16/16.
 */
public class AlbumHelper {
    public static final int MAX_SELECT = 9;
    public static ArrayList<LocalImage> selectedList = new ArrayList<>(MAX_SELECT);

    public static Bitmap resizeImage(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i++;
        }
        return bitmap;
    }
}
