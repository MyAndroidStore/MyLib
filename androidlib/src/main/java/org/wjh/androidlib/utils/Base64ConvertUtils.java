package org.wjh.androidlib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Base64ConvertUtils {

    public static String file2Base64(String file) {

        String convertResult = "";

        Bitmap bitmap = BitmapFactory.decodeFile(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        byte[] imgBytes = out.toByteArray();

        convertResult = Base64.encodeToString(imgBytes, Base64.NO_WRAP);

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }


        return convertResult;

    }
}
