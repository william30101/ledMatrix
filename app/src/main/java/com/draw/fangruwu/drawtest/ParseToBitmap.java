package com.draw.fangruwu.drawtest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by williamwu on 23/10/2017.
 */

public class ParseToBitmap {

    public Bitmap stringToBufferedImage(String text, float textSize, int textColor, ArrayList<Integer> pixel) {
        // adapted from https://stackoverflow.com/a/8799344/1476989
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);

        int trueWidth = width;
        if(width>height)height=width; else width=height;
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, width/2-trueWidth/2, baseline, paint);

        int wid = image.getWidth();
        int hei = image.getHeight();
        int[] store = new int[wid * hei];
        image.getPixels(store, 0, wid, 0, 0, wid, hei);

        for (int j=0 ; j < store.length; j++) {

            pixel.add((store[j] != 0) ? 1 : 0);
        }

        return image;

    }
}
