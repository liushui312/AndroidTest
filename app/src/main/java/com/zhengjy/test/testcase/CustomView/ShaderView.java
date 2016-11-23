package com.zhengjy.test.testcase.CustomView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.zhengjy.common.utils.ScreenUtils;
import com.zhengjy.test.R;

/**
 * Created by zhengjy on 2016/11/23.
 */

public class ShaderView extends View {
    private static final int RECT_SIZE = 400;// 矩形尺寸的一半
    private final Bitmap bitmap1;

    private Paint mPaint1;// 画笔1
    private Paint mPaint2;// 画笔1


    private int left, top, right, bottom;// 矩形坐上右下坐标

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);


        // 实例化画笔
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // 获取位图
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow3);

        BitmapShader bitmapShader1 = new BitmapShader(bitmap1, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        BitmapShader bitmapShader2 = new BitmapShader(bitmap2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        Matrix shaderMatrix = new Matrix();
        float scale =  0.5f / 1.2f;
        shaderMatrix.setScale(scale, scale);
        bitmapShader1.setLocalMatrix(shaderMatrix);
        bitmapShader2.setLocalMatrix(shaderMatrix);

        // 设置着色器
        mPaint1.setShader(bitmapShader1);
        mPaint2.setShader(bitmapShader2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(50, 50, 50, mPaint1);
        canvas.drawCircle(150, 150, 50, mPaint2);
    }
}
