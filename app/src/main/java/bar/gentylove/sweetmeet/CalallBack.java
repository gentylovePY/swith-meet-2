package bar.gentylove.sweetmeet;



import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public abstract class CalallBack {
    public abstract void OnFinishListener();

    private List<LinearLayoutManager> LinerLayoutManagers;

    public void setAllLinerManagers(List<LinearLayoutManager> linerLayoutManagers) {
        this.LinerLayoutManagers = linerLayoutManagers;
    }

    public List<LinearLayoutManager> getLinerLayoutManagers() {
        return LinerLayoutManagers;
    }
}
