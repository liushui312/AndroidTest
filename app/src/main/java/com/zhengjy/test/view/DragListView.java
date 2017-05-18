package com.zhengjy.test.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhengjy.test.R;


public class DragListView extends ListView {
    private static final String TAG = "DragListView";

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;

    // 被拖拽的项(item)
    private ImageView mDragImageView;
    private int mStartPosition;// 拖动项开始的位置
    private int mDragPosition;// 当前拖动项在列表中的位置.
    private int mEndPosition;// 拖动项结束的位置.

    private int mDragPoint;// 在当前数据项中的位置
    private int mDragOffset;// 当前视图和屏幕的距离(这里只使用了y方向上)

    private int mUpScrollBounce;// 拖动的时候，开始向上滚动的边界
    private int mDownScrollBounce;// 拖动的时候，开始向下滚动的边界

    private final static int mStep = 1;// ListView 滑动步伐.
    private int mCurrentStep;// 当前步伐.

    private boolean mIsMoving = false;

    private int mItemVerticalSpacing = 0;

    private boolean mHasGetSpacing = false;

    private static final int ANIMATION_DURATION = 200;

    private boolean mIsSameDragDirection = true;
    //-1,0: down, 1: up
    private int mLastFlag = -1;

    public DragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        init();
    }

    private void init() {
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    private void getSpacing() {
        mHasGetSpacing = true;

        // 取得向上滚动的边际，大概为该控件的1/3
        mUpScrollBounce = getHeight() / 3;
        // 取得向下滚动的边际，大概为该控件的2/3
        mDownScrollBounce = getHeight() * 2 / 3;

        int[] tempLocation0 = new int[2];
        int[] tempLocation1 = new int[2];

        ViewGroup itemView0 = (ViewGroup) getChildAt(0);
        ViewGroup itemView1 = (ViewGroup) getChildAt(1);

        if (itemView0 != null) {
            itemView0.getLocationOnScreen(tempLocation0);
        } else {
            return;
        }

        if (itemView1 != null) {
            itemView1.getLocationOnScreen(tempLocation1);
            mItemVerticalSpacing = Math.abs(tempLocation1[1] - tempLocation0[1]);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN && !mIsMoving) {
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            mEndPosition = mStartPosition = mDragPosition = pointToPosition(x, y);

            if (mDragPosition == AdapterView.INVALID_POSITION) {
                return super.onInterceptTouchEvent(ev);
            }

            if (!mHasGetSpacing) {
                getSpacing();
            }

            // 获取当前位置的视图(可见状态)
            ViewGroup dragView = (ViewGroup) getChildAt(mDragPosition - getFirstVisiblePosition());

            DragListAdapter adapter = (DragListAdapter) getAdapter();

            // 获取到的dragPoint其实就是在你点击指定item项中的高度.
            mDragPoint = y - dragView.getTop();
            // 这个值是固定的:其实就是ListView这个控件与屏幕最顶部的距离（一般为标题栏+状态栏）.
            mDragOffset = (int) (ev.getRawY() - y);

            // 获取可拖拽的图标
            View dragIcon = dragView.findViewById(R.id.drag);

            // x > dragIcon.getLeft() - 20这句话为了更好的触摸（-20可以省略）
            if (dragIcon != null && x > dragIcon.getLeft() - 20) {
                dragView.destroyDrawingCache();
                dragView.setDrawingCacheEnabled(true);
                dragView.setBackgroundColor(0x55555555);
                Bitmap bm = Bitmap.createBitmap(dragView.getDrawingCache(true));
                hideDropItem();
                adapter.setInvisiblePosition(mStartPosition);
                adapter.notifyDataSetChanged();
                startDrag(bm, y);
                mIsMoving = false;

                adapter.copyList();
            }
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private void hideDropItem() {
        final DragListAdapter adapter = (DragListAdapter) this.getAdapter();
        adapter.showDropItem(false);
    }

    /**
     * 触摸事件处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mDragImageView != null && mDragPosition != INVALID_POSITION) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int) ev.getY();
                    onDrag(moveY);
                    testAnimation(moveY);
                    break;
                case MotionEvent.ACTION_UP:
                    int upY = (int) ev.getY();
                    stopDrag();
                    onDrop(upY);
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
                default:
                    break;
            }
            return true;
        }

        return super.onTouchEvent(ev);
    }


    private void onChangeCopy(int last, int current) {
        DragListAdapter adapter = (DragListAdapter) getAdapter();
        if (last != current) {
            adapter.exchangeCopy(last, current);
        }

    }

    private void testAnimation(int y) {
        final DragListAdapter adapter = (DragListAdapter) getAdapter();
        int tempPosition;
        if (y < 0) {
            tempPosition = 0;
        } else if (y > getHeight()) {
            tempPosition = getCount() - 1;
        } else {
            tempPosition = pointToPosition(0, y);
        }
		Log.i(TAG, "y:" + y + ", tempPosition " + tempPosition +", mEndPosition:" + mEndPosition);
        if (tempPosition == INVALID_POSITION || tempPosition == mEndPosition) {
            return;
        }
        mDragPosition = tempPosition;

        onChangeCopy(mEndPosition, mDragPosition);
        int MoveNum = mDragPosition - mEndPosition;
        int count = Math.abs(MoveNum);
        for (int i = 1; i <= count; i++) {
            int xAbsOffset, yAbsOffset;
            //向下drag
            int holdPosition;
            if (MoveNum > 0) {
                if (mLastFlag == -1) {
                    mLastFlag = 0;
                    mIsSameDragDirection = true;
                }
                if (mLastFlag == 1) {
                    mLastFlag = 0;
                    mIsSameDragDirection = !mIsSameDragDirection;
                }
                if (mIsSameDragDirection) {
                    holdPosition = mEndPosition + 1;
                } else {
                    if (mStartPosition < tempPosition) {
                        holdPosition = mEndPosition + 1;
                        mIsSameDragDirection = !mIsSameDragDirection;
                    } else {
                        holdPosition = mEndPosition;
                    }
                }
                xAbsOffset = 0;
                yAbsOffset = -mItemVerticalSpacing;
                mEndPosition++;
            }
            //向上drag
            else {
                if (mLastFlag == -1) {
                    mLastFlag = 1;
                    mIsSameDragDirection = true;
                }
                if (mLastFlag == 0) {
                    mLastFlag = 1;
                    mIsSameDragDirection = !mIsSameDragDirection;
                }
                if (mIsSameDragDirection) {
                    holdPosition = mEndPosition - 1;
                } else {
                    if (mStartPosition > tempPosition) {
                        holdPosition = mEndPosition - 1;
                        mIsSameDragDirection = !mIsSameDragDirection;
                    } else {
                        holdPosition = mEndPosition;
                    }
                }
                xAbsOffset = 0;
                yAbsOffset = mItemVerticalSpacing;
                mEndPosition--;
            }

            adapter.setHeight(mItemVerticalSpacing);
            adapter.setLastFlag(mLastFlag);

            ViewGroup moveView = (ViewGroup) getChildAt(holdPosition - getFirstVisiblePosition());

            Animation animation;
            if (mIsSameDragDirection) {
                animation = getFromSelfAnimation(xAbsOffset, yAbsOffset);
            } else {
                animation = getToSelfAnimation(xAbsOffset, -yAbsOffset);
            }
            moveView.startAnimation(animation);
        }
    }

    private void onDrop(int x, int y) {
        final DragListAdapter adapter = (DragListAdapter) getAdapter();
        adapter.setInvisiblePosition(-1);
        adapter.showDropItem(true);
        adapter.notifyDataSetChanged();

//		doDropAnimation(x,y);
    }

    /**
     * 准备拖动，初始化拖动项的图像
     *
     * @param bm
     * @param y
     */
    private void startDrag(Bitmap bm, int y) {
        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.gravity = Gravity.TOP;
        mWindowParams.x = 0;
        mWindowParams.y = y - mDragPoint + mDragOffset;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE// 不需获取焦点
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE// 不需接受触摸事件
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON// 保持设备常开，并保持亮度不变。
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;// 窗口占满整个屏幕，忽略周围的装饰边框（例如状态栏）。此窗口需考虑到装饰边框的内容。

        // mWindowParams.format = PixelFormat.TRANSLUCENT;// 默认为不透明，这里设成透明效果.
        mWindowParams.windowAnimations = 0;// 窗口所使用的动画设置

        mWindowParams.alpha = 0.8f;
        mWindowParams.format = PixelFormat.TRANSLUCENT;

        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bm);

        mWindowManager.addView(imageView, mWindowParams);
        mDragImageView = imageView;
    }

    /**
     * 拖动执行，在Move方法中执行
     *
     * @param y
     */
    public void onDrag(int y) {
        // 拖拽view的top值不能＜0，否则则出界.
        if (mDragImageView != null && y >= 0 && y < getHeight()) {
            mWindowParams.alpha = 1.0f;
            mWindowParams.y = y - mDragPoint + mDragOffset;
            mWindowManager.updateViewLayout(mDragImageView, mWindowParams);
        }

        doScroller(y);
    }

    public void doScroller(int y) {
        // ListView需要下滑
        if (y < mUpScrollBounce) {
            mCurrentStep = mStep + (mUpScrollBounce - y) / 10;
        }// ListView需要上滑
        else if (y > mDownScrollBounce) {
            mCurrentStep = -(mStep + (y - mDownScrollBounce)) / 10;
        } else {
            mCurrentStep = 0;
        }

        // 获取你拖拽滑动到位置及显示item相应的view上（注：可显示部分）（position）
        View view = getChildAt(mDragPosition - getFirstVisiblePosition());
        // 真正滚动的方法setSelectionFromTop()
        setSelectionFromTop(mDragPosition, view.getTop() + mCurrentStep);
    }

    /**
     * 停止拖动，删除影像
     */
    public void stopDrag() {
        mIsMoving = false;
        if (mDragImageView != null) {
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
        }
        mIsSameDragDirection = true;
        mLastFlag = -1;
        DragListAdapter adapter = (DragListAdapter) getAdapter();
        adapter.setLastFlag(mLastFlag);
        adapter.pasteList();
    }

    /**
     * 拖动放下的时候
     */
    public void onDrop(int y) {
        onDrop(0, y);
    }


    public Animation getFromSelfAnimation(int x, int y) {
        TranslateAnimation go = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, x,
                Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, y);
        go.setInterpolator(new AccelerateDecelerateInterpolator());
        go.setFillAfter(true);
        go.setDuration(ANIMATION_DURATION);
        go.setInterpolator(new AccelerateInterpolator());
        return go;
    }

    public Animation getToSelfAnimation(int x, int y) {
        TranslateAnimation go = new TranslateAnimation(
                Animation.ABSOLUTE, x, Animation.RELATIVE_TO_SELF, 0,
                Animation.ABSOLUTE, y, Animation.RELATIVE_TO_SELF, 0);
        go.setInterpolator(new AccelerateDecelerateInterpolator());
        go.setFillAfter(true);
        go.setDuration(ANIMATION_DURATION);
        go.setInterpolator(new AccelerateInterpolator());
        return go;
    }
}