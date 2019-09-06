package chn.fz.thatjay.scrolleditview.view;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import chn.fz.thatjay.scrolleditview.R;


public class ScrollMulrowsEditText extends AppCompatEditText {

    private final String TAG = "ScMulrowsEditText";
    //滑动距离的最大边界
    private int mOffsetHeight;

    private int mHeight;


    private int mVert = 0;

    private Context mContext;

    public ScrollMulrowsEditText(Context context) {
        this(context, null);
    }

    public ScrollMulrowsEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollMulrowsEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        initAttribute(context, attrs, defStyleAttr);
    }

    private void init() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int paddingTop;
        int paddingBottom;
        int height;
        int mLayoutHeight;

        //获得内容面板
        Layout mLayout = getLayout();
        //获得内容面板的高度
        mLayoutHeight = mLayout.getHeight();
        //获取上内边距
        paddingTop = getTotalPaddingTop();
        //获取下内边距
        paddingBottom = getTotalPaddingBottom();

        //获得控件的实际高度
        height = mHeight;  //getHeight();

        //计算滑动距离的边界
        mOffsetHeight = mLayoutHeight + paddingTop + paddingBottom - height;

        setOnTouchListener();
    }

    private void initAttribute(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScrollMulrowsEditText, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.ScrollMulrowsEditText_height:
                    mHeight = array.getDimensionPixelSize(attr, 0);
                    break;
            }
        }
        array.recycle();
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        mVert = vert;
        Log.d(TAG,"mOffsetHeight == " + mOffsetHeight + "  ,, vert ===  " + vert );
        if (vert == mOffsetHeight || vert == 0) {
            //这里触发父布局或祖父布局的滑动事件
            getParent().requestDisallowInterceptTouchEvent(false);
            Log.d(TAG, "vert requestDisallowInterceptTouchEvent  false ");
        }
    }

    //滑动到上边缘
    public boolean isUpperEdge(){
        return mVert == 0;
    }

    //滑动到下边缘
    public boolean isLowerEdge(){
        return mVert == mOffsetHeight;
    }

    private float scrollBeginY;
    public void setOnTouchListener(){
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //canScrollVertically()方法为判断指定方向上是否可以滚动,参数为正数或负数,负数检查向上是否可以滚动,正数为检查向下是否可以滚动
                if(MotionEvent.ACTION_DOWN == event.getAction()){
                    scrollBeginY = event.getY();
                    v.getParent().requestDisallowInterceptTouchEvent(true);//要求父类布局不在拦截触摸事件
                    return false;
                }
                Log.d(TAG, "event.getY" + event.getY());//edittext 如果在最边缘，getY得到的也不是固定的值
                if(canScrollVertically(1)){//可以向下滚动
                    if(isUpperEdge() && event.getY() >= scrollBeginY){
                        v.getParent().requestDisallowInterceptTouchEvent(false);//交给父布局
                    } else {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else if(canScrollVertically(-1)){
                    if(isLowerEdge() && event.getY() <= scrollBeginY){
                        v.getParent().requestDisallowInterceptTouchEvent(false);//交给父布局
                    } else {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    v.getParent().requestDisallowInterceptTouchEvent(false);//交给父布局
                }
                //getY  下== 小
                return false;
            }
        });
    }


}
