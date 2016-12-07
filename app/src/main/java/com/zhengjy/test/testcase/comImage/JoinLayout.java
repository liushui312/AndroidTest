package com.zhengjy.test.testcase.comImage;

public class JoinLayout {

    public static final String TAG = "JoinLayout";

    public static int HEAD_COUNT_MAX = 5;

    /**
     *  记录头像的缩放比例和边距比例，scales[0]缩放 scales[1]边距
     */
    private static final float[][] mScales = { new float[] { 1f, 1f },
            new float[] { 0.54f, 0.78f }, new float[] { 0.44f, 0.96f },
            new float[] { 0.44f, 1.136f }, new float[] { 0.36f, 0.96f } };

    public static float[] getScales(int count) {
        return count > 0 && count <= mScales.length ? mScales[count - 1] : null;
    }


    /**
     *  获取每个头像的偏置量
     */
    public static float[] offset(int count, int index, float width, float[] scales) {
        switch (count) {
            case 1:
                return offset1(index, width, scales);
            case 2:
                return offset2(index, width, scales);
            case 3:
                return offset3(index, width, scales);
            case 4:
                return offset4(index, width, scales);
            case 5:
                return offset5(index, width, scales);
            default:
                break;
        }
        return new float[] { 0f, 0f };
    }

    /**
     * 5个头像
     *
     * @param index
     *            下标
     * @param width
     *            画布边长（正方形）
     * @param scales
     *            scales[0]缩放 scales[1]边距
     * @return 下标index X，Y轴坐标
     */
    private static float[] offset5(int index, float width, float[] scales) {
        // 圆的直径
        float cd = (float) width * scales[0];
        // 边距
        float s1 = -cd * scales[1];

        float x1 = 0;
        float y1 = s1;

        float x2 = (float) (s1 * Math.cos(19 * Math.PI / 180));
        float y2 = (float) (s1 * Math.sin(18 * Math.PI / 180));

        float x3 = (float) (s1 * Math.cos(54 * Math.PI / 180));
        float y3 = (float) (-s1 * Math.sin(54 * Math.PI / 180));

        float x4 = (float) (-s1 * Math.cos(54 * Math.PI / 180));
        float y4 = (float) (-s1 * Math.sin(54 * Math.PI / 180));

        float x5 = (float) (-s1 * Math.cos(19 * Math.PI / 180));
        float y5 = (float) (s1 * Math.sin(18 * Math.PI / 180));

        // Log.d(TAG, "x1:" + x1 + "/y1:" + y1);
        // Log.d(TAG, "x2:" + x2 + "/y2:" + y2);
        // Log.d(TAG, "x3:" + x3 + "/y3:" + y3);
        // Log.d(TAG, "x4:" + x4 + "/y4:" + y4);
        // Log.d(TAG, "x5:" + x5 + "/y5:" + y5);

        // 居中 Y轴偏移量
        float xx1 = (width - cd - y3 - s1) / 2;
        // 居中 X轴偏移量
        float xxc1 = (width - cd) / 2;
        // xx1 = xxc1 = -s1;
        // xx1 = xxc1 = 0;
        switch (index) {
            case 0:
                // return new float[] { s1 + xxc1, xx1 };
                return new float[] { x1 + xxc1, y1 + xx1 };
            case 1:
                return new float[] { x2 + xxc1, y2 + xx1 };
            case 2:
                return new float[] { x3 + xxc1, y3 + xx1 };
            case 3:
                return new float[] { x4 + xxc1, y4 + xx1 };
            case 4:
                return new float[] { x5 + xxc1, y5 + xx1 };
            default:
                break;
        }
        return new float[] { 0f, 0f };
    }

    /**
     * 4个头像
     *
     * @param index
     *            下标
     * @param width
     *            画布边长（正方形）
     * @param scales
     *            scales[0]缩放 scales[1]边距
     * @return 下标index X，Y轴坐标
     */
    private static float[] offset4(int index, float width, float[] scales) {
        // 圆的直径
        float cd = (float) width * scales[0];
        // 边距
        float s1 = cd * scales[1];

        float x1 = 0;
        float y1 = 0;

        float x2 = s1;
        float y2 = y1;

        float x3 = s1;
        float y3 = s1;

        float x4 = x1;
        float y4 = y3;

        // Log.d(TAG, "x1:" + x1 + "/y1:" + y1);
        // Log.d(TAG, "x2:" + x2 + "/y2:" + y2);
        // Log.d(TAG, "x3:" + x3 + "/y3:" + y3);
        // Log.d(TAG, "x4:" + x4 + "/y4:" + y4);

        // 居中 X轴偏移量
        float xx1 = (width - cd - s1) / 2;
        switch (index) {
            case 0:
                return new float[] { x1 + xx1, y1 + xx1 };
            case 1:
                return new float[] { x2 + xx1, y2 + xx1 };
            case 2:
                return new float[] { x3 + xx1, y3 + xx1 };
            case 3:
                return new float[] { x4 + xx1, y4 + xx1 };
            default:
                break;
        }
        return new float[] { 0f, 0f };
    }

    /**
     * 3个头像
     *
     * @param index
     *            下标
     * @param width
     *            画布边长（正方形）
     * @param scales
     *            scales[0]缩放 scales[1]边距
     * @return 下标index X，Y轴坐标
     */
    private static float[] offset3(int index, float width, float[] scales) {
        // 圆的直径
        float cd = (float) width * scales[0];
        // 边距
        float s1 = cd * scales[1];
        // 第二个圆的 Y坐标
        float y2 = s1 * (3 / 2);
        // 第二个圆的 X坐标
        float x2 = s1 - y2 / 1.73205f;
        // 第三个圆的 X坐标
        float x3 = s1 * 2 - x2;
        // 居中 Y轴偏移量
        float xx1 = (width - cd - y2) / 2;
        // 居中 X轴偏移量
        float xxc1 = (width - cd) / 2 - s1;
        // xx1 = xxc1 = 0;
        switch (index) {
            case 0:
                return new float[] { s1 + xxc1, xx1 };
            case 1:
                return new float[] { x2 + xxc1, y2 + xx1 };
            case 2:
                return new float[] { x3 + xxc1, y2 + xx1 };
            default:
                break;
        }
        return new float[] { 0f, 0f };
    }

    /**
     * 2个头像
     *
     * @param index
     *            下标
     * @param width
     *            画布边长（正方形）
     * @param scales
     *            scales[0]缩放 scales[1]边距
     * @return 下标index X，Y轴坐标
     */
    private static float[] offset2(int index, float width, float[] scales) {
        // 圆的直径
        float cd = (float) width * scales[0];
        // 边距
        float s1 = cd * scales[1];

        float x1 = 0;
        float y1 = 0;

        float x2 = s1;
        float y2 = s1;

        // Log.d(TAG, "x1:" + x1 + "/y1:" + y1);
        // Log.d(TAG, "x2:" + x2 + "/y2:" + y2);

        // 居中 X轴偏移量
        float xx1 = (width - cd - s1) / 2;
        switch (index) {
            case 0:
                return new float[] { x1 + xx1, y1 + xx1 };
            case 1:
                return new float[] { x2 + xx1, y2 + xx1 };
            default:
                break;
        }
        return new float[] { 0f, 0f };
    }

    /**
     * 1个头像
     *
     * @param index
     *            下标
     * @param width
     *            画布边长（正方形）
     * @param scales
     *            scales[0]缩放 scales[1]边距
     * @return 下标index X，Y轴坐标
     */
    private static float[] offset1(int index, float width, float[] scales) {
        // 圆的直径
        float cd = (float) width * scales[0];
        float offset = (width - cd) / 2;
        return new float[] { offset, offset };
    }
}
