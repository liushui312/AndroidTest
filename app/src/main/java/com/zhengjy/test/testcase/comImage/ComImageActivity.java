package com.zhengjy.test.testcase.comImage;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.zhengjy.test.R;

public class ComImageActivity extends Activity {

    ArrayList<Bitmap> mBmps1 = new ArrayList<Bitmap>();
    ArrayList<Bitmap> mBmps2 = new ArrayList<Bitmap>();
    ArrayList<Bitmap> mBmps3 = new ArrayList<Bitmap>();
    ArrayList<Bitmap> mBmps4 = new ArrayList<Bitmap>();
    ArrayList<Bitmap> mBmps5 = new ArrayList<Bitmap>();

    CircularImageView mImageView1;
    CircularImageView mImageView2;
    CircularImageView mImageView3;
    CircularImageView mImageView4;
    CircularImageView mImageView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_image);

        // test data
        Bitmap avatar1 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow1);
        Bitmap avatar2 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow2);
        Bitmap avatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow3);
        Bitmap avatar4 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow4);
        Bitmap avatar5 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow5);

        mBmps1.add(avatar1);
        mBmps2.add(avatar1);
        mBmps3.add(avatar1);
        mBmps4.add(avatar1);
        mBmps5.add(avatar1);

        mBmps2.add(avatar2);
        mBmps3.add(avatar2);
        mBmps4.add(avatar2);
        mBmps5.add(avatar2);

        mBmps3.add(avatar3);
        mBmps4.add(avatar3);
        mBmps5.add(avatar3);

        mBmps4.add(avatar4);
        mBmps5.add(avatar4);

        mBmps5.add(avatar5);

        mImageView1 = (CircularImageView) findViewById(R.id.circularImageView1);
        mImageView2 = (CircularImageView) findViewById(R.id.circularImageView2);
        mImageView3 = (CircularImageView) findViewById(R.id.circularImageView3);
        mImageView4 = (CircularImageView) findViewById(R.id.circularImageView4);
        mImageView5 = (CircularImageView) findViewById(R.id.circularImageView5);

        mImageView1.setImageBitmaps(mBmps1);
        mImageView2.setImageBitmaps(mBmps2);
        mImageView3.setImageBitmaps(mBmps3);
        mImageView4.setImageBitmaps(mBmps4);
        mImageView5.setImageBitmaps(mBmps5);

        mImageView1 = (CircularImageView) findViewById(R.id.circularImageView21);
        mImageView2 = (CircularImageView) findViewById(R.id.circularImageView22);
        mImageView3 = (CircularImageView) findViewById(R.id.circularImageView23);
        mImageView4 = (CircularImageView) findViewById(R.id.circularImageView24);
        mImageView5 = (CircularImageView) findViewById(R.id.circularImageView25);

        mImageView1.setImageBitmaps(mBmps1);
        mImageView2.setImageBitmaps(mBmps2);
        mImageView3.setImageBitmaps(mBmps3);
        mImageView4.setImageBitmaps(mBmps4);
        mImageView5.setImageBitmaps(mBmps5);
    }
}
