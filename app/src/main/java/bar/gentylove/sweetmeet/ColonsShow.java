package bar.gentylove.sweetmeet;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class ColonsShow extends RecyclerView {
    public ColonsShow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return true;
    }
}