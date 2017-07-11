package me.tomaz.lab.barcodewidget.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by TomazWang on 10/07/2017.
 */

public class BarcodeUtils {


    public static Bitmap getBarcodeBmp(String code, int width, int height){
        Bitmap bmp = null;
        try {
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix matrix  = writer.encode(code, BarcodeFormat.CODE_39, width, height);
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    bmp.setPixel(x, y, matrix.get(x,y) ? Color.BLACK : Color.WHITE);
                }
            }

        } catch (WriterException ignored) {}

        return bmp;
    }

}
