package bar.gentylove.sweetmeet;


import androidx.recyclerview.widget.RecyclerView;

public class ScrollingColons extends RecyclerView.OnScrollListener {
    private CalallBack calallBack;

    public ScrollingColons(CalallBack calallBack) {
        this.calallBack = calallBack;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                calallBack.OnFinishListener();
        }

    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

    }
}