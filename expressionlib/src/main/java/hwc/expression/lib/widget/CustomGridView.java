package hwc.expression.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by jian on 2016/6/23.
 * mabeijianxi@gmail.com
 */
public class CustomGridView extends GridView {

    public CustomGridView(Context context) {

        super(context);

    }

    public CustomGridView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override

    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {

            return true;  //禁止GridView滑动
        }

        return super.dispatchTouchEvent(ev);
    }

}
