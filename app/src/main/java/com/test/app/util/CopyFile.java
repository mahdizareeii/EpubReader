package com.test.app.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFile {

    private Context context;

    public CopyFile(Context context) {
        this.context = context;
    }

    public void copyFileFromAsset(String fileName) {
        if (!checkFileExist(fileName)) {
            doCopyFile(fileName);
        }
    }

    private boolean checkFileExist(String fileName) {
        return new File(getPath() + "/" + fileName).exists();
    }

    private void doCopyFile(String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            OutputStream outputStream = new FileOutputStream(getPath() + "/" + fileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read()) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath() {
        return context.getExternalFilesDir(null).getPath();
    }
}
