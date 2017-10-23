package com.draw.fangruwu.drawtest;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by fangru.wu on 10/23/17.
 */

public class SurfaceDemo extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = "Test SurfaceView";
    private float RectLeft, RectTop,RectRight,RectBottom ;
    int  deviceHeight,deviceWidth;
    int matrixWidth = 16;
    int matrixHeight = 5;
    public static int[][] tileArray;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurfaceView view = new SurfaceView(this);
        setContentView(view);
        view.getHolder().addCallback(this);
        try {
            loadTileMap("test",3,16);
        } catch (IOException e) {
            e.printStackTrace();
        }
        deviceWidth=getScreenWidth();

        deviceHeight=getScreenHeight();

    }

    public static int getScreenWidth() {

        return Resources.getSystem().getDisplayMetrics().widthPixels;

    }



    public static int getScreenHeight() {

        return Resources.getSystem().getDisplayMetrics().heightPixels;

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        tryDrawing(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        tryDrawing(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void tryDrawing(SurfaceHolder holder) {
        Log.i(TAG, "Trying to draw...");

        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            Log.e(TAG, "Cannot draw onto the canvas as it's null");
        } else {
            drawMyStuff(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawMyStuff(final Canvas canvas) {
        Random random = new Random();
        Log.i(TAG, "Drawing...");

        int heightSize = Math.round(deviceHeight / matrixHeight) ;
        int widthSize = Math.round(deviceWidth / matrixWidth) ;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);

        for (int i=0; i< matrixHeight ; i++) {
            for (int j=0; j < matrixWidth; j++) {
                Rect rec=new Rect(j * widthSize, i * widthSize, j * widthSize + widthSize, i * widthSize + widthSize);
                canvas.drawRect(rec,paint);
            }
        }
    }

    public void loadTileMap(String str, int height, int width) throws IOException {

        // convert String into InputStream
        //InputStream is = new ByteArrayInputStream(str.getBytes());
        InputStream is = new ByteArrayInputStream("GG".getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        tileArray = new int[width][height];

        for (int i = 0; i < width; i++) {
            line = reader.readLine();
            if (line == null) {
                reader.close();
            }
            for (int j = 0; j < height; j++) {
                int k = Integer.parseInt(line.substring(j, j+1));
                tileArray[i][j] = k;
            }
        }
    }
}
