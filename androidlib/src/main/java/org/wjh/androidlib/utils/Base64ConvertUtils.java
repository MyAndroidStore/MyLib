package org.wjh.androidlib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Base64ConvertUtils {

    public static String file2Base64(String file) {

        Bitmap bitmap = BitmapFactory.decodeFile(file);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();

            return Base64.encodeToString(imgBytes, Base64.NO_WRAP);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
