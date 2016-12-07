package com.zhengjy.test.testcase.comImage;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;

public class JoinBitmaps {

    private static final String TAG = "JoinBitmaps";

    /**
     *  获取绘制每个头像的画笔
     * @param width  头像的宽度
     * @param bitmaps 头像 bitmap
     * @return 画笔
     */
    public static final List<Paint> getPaints(int width, List<Bitmap> bitmaps) {
        if (bitmaps == null) {
            Log.e(TAG, "bitmaps is null");
            return null;
        }
        int count = Math.min(bitmaps.size(), JoinLayout.HEAD_COUNT_MAX);
        float[] scales = JoinLayout.getScales(count);
        if (scales == null) {
            return null;
        }

        List<Paint> paints = new ArrayList<>();
        Matrix matrixJoin = new Matrix();
        // scale as join getScales
        matrixJoin.postScale(scales[0], scales[0]);

        for (int index = 0; index < count; index++) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Matrix matrix = new Matrix();
            matrix.postScale((float) width / bitmaps.get(index).getWidth(),
                    (float) width / bitmaps.get(index).getHeight());
            matrix.postConcat(matrixJoin);
            BitmapShader bitmapShader = new BitmapShader(bitmaps.get(index), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            bitmapShader.setLocalMatrix(matrix);
            paint.setShader(bitmapShader);
            paints.add(paint);
        }

        return paints;
    }

    /**
     *   绘制头像
     * @param canvas  画布
     * @param paints  画笔
     * @param width   头像的宽度
     */
    public static final void join(Canvas canvas, List<Paint> paints, int width) {
        if (paints == null) {
            return;
        }

        int count = Math.min(paints.size(), JoinLayout.HEAD_COUNT_MAX);
        float[] scales = JoinLayout.getScales(count);
        if (scales == null) {
            return;
        }

        int radius = (int) (width * scales[0] / 2);
        Log.d(TAG, "width:" + width + ", getScales[0]: " + scales[0] + ", radius is " + radius);

        canvas.save();

        for (int index = 0; index < paints.size(); index++) {
            canvas.save();

            float[] offset = JoinLayout.offset(count, index, width, scales);
            Log.d(TAG, "index:" + index + ", offset[0]:" + offset[0] + ", offset[1]" + offset[1]);
            canvas.translate(offset[0], offset[1]);

            canvas.drawCircle(radius, radius, radius, paints.get(index));
            canvas.restore();
        }

        canvas.restore();
    }
}
