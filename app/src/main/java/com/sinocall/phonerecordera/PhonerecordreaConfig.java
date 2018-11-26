package com.sinocall.phonerecordera;

import android.os.Environment;

import java.io.File;

/**
 * Created by qingchao on 2017/12/13.
 *
 */

public class PhonerecordreaConfig {
    public static final String BASE_DIR = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Phonerecordrea/";

    static {
        File file = new File(BASE_DIR);
        if (file.exists() && !file.isDirectory()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
