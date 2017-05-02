package com.soyoungboy.base.util;

import android.content.Context;
import java.io.File;

/**
 * TODO: 图文文件缓冲
 *
 * @author soyoungboy
 * @version 1.0.0
 * @date 2014-6-25 下午2:57:33
 */
public class FileCache {

    public static final String TAG = FileCache.class.getSimpleName();

    private File cacheDir;


    public FileCache(Context context) {
        // Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState()
            .equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),
                "belle" + File.separator + "bt"
                    + File.separator + "wxm_pic_cache");
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }


    public File getFile(String url) {
        // I identify images by hashcode. Not a perfect solution, good for the
        // demo.
        String filename = String.valueOf(url.hashCode());
        return new File(cacheDir, filename);

    }


    public void clear() {
        File[] files = cacheDir.listFiles();
        for (File f : files) {
            f.delete();
        }
    }

}