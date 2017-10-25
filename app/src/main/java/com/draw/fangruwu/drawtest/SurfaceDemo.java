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

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by fangru.wu on 10/23/17.
 */

public class SurfaceDemo extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = "Test SurfaceView";
    private float RectLeft, RectTop,RectRight,RectBottom ;
    int  deviceHeight,deviceWidth;
    int matrixWidth = 18;
    int matrixHeight =18;
    ArrayList<Integer> pixels;
    int [][] matrix = new int[18][18];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        SurfaceView view = new SurfaceView(this);
        setContentView(view);
        view.getHolder().addCallback(this);
//        try {
//            loadTileMap("test",3,16);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        deviceWidth=getScreenWidth();

        deviceHeight=getScreenHeight();

        pixels = new ArrayList<>();

        ParseToBitmap parse = new ParseToBitmap();

        parse.stringToBufferedImage("T", 16, Color.RED, pixels);

        for (int i=0; i < 18; i++) {
            for (int j=0 ; j < 18; j++) {
                matrix[i][j] = pixels.get(i + j * 18);
            }
        }
        rotateByNinetyToRight(matrix);

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

            canvas.drawColor(Color.WHITE);
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

        Paint paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(Color.BLACK);
        paintFill.setStrokeWidth(3);


        for (int i=0; i< matrixHeight ; i++) {
            for (int j=0; j < matrixWidth; j++) {

                if (matrix[i][j] == 1) {
                    Rect rec=new Rect(j * heightSize, i * heightSize, j * heightSize + heightSize, i * heightSize + heightSize);
                    canvas.drawRect(rec,paintFill);
                }else {
                    Rect rec=new Rect(j * heightSize, i * heightSize, j * heightSize + heightSize, i * heightSize + heightSize);
                    canvas.drawRect(rec,paint);
                }

            }
        }
    }

    public void swapRows(int[][] m) {
        for (int  i = 0, k = m.length - 1; i < k; ++i, --k) {
            int[] x = m[i];
            m[i] = m[k];
            m[k] = x;
        }
    }

    public void rotateByNinetyToRight(int[][] m) {
        swapRows(m);
        transpose(m);
    }

    private void transpose(int[][] m) {

        for (int i = 0; i < m.length; i++) {
            for (int j = i; j < m[0].length; j++) {
                int x = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = x;
            }
        }
    }

}
