package bar.gentylove.sweetmeet;

import android.app.Activity;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColonsBilderShow {
    private Activity NainACtivity;
    private List<ColonsShow> colonsShows;
    private List<Integer> GiveDrawablesId;
    private Boolean WorkedColons;
    private Float ScrollingTimes = 3f;
    private Float TimePerInch = 1f;
    private Integer TimeColonsGo = 1800;
    private Integer ChildTimes = 1500;
    private CalallBack calallBack;
    private List<LinearLayoutManager> LinerlayoutManagers;

    private ColonsBilderShow() {
    }

    private void AddAllSlots(Integer... slotsViewId) {
        for (Integer slotId : slotsViewId) {
            colonsShows.add(NainACtivity.findViewById(slotId));
        }
    }

    private void addDrawablesPictures(Integer... drawableIds) {
        this.GiveDrawablesId.addAll(Arrays.asList(drawableIds));
    }

    private void buildColons() {
        Float timePerInch = ScrollingTimes;
        for (ColonsShow colonsShow : colonsShows) {
            SpeedSlot.setScrollingTime(timePerInch);
            RecyclerView.LayoutManager mLayoutManager = new SpeedSlot(NainACtivity);
            colonsShow.setLayoutManager(mLayoutManager);
            LinerlayoutManagers.add((LinearLayoutManager) mLayoutManager);
            ColonsAdapterView mAdapter = new ColonsAdapterView(GiveDrawablesId);
            colonsShow.setAdapter(mAdapter);
            timePerInch += TimePerInch;
        }
        calallBack.setAllLinerManagers(LinerlayoutManagers);
        colonsShows.get(colonsShows.size() - 1).addOnScrollListener(new ScrollingColons(calallBack));
        GiveDrawablesId.clear();
    }

    public boolean startPlay() {
        if (WorkedColons) {
            return false;
        } else {
            WorkedColons = true;
            Integer tempTime = this.TimeColonsGo;
            for (final ColonsShow colonsShow : colonsShows) {
                tempTime += ChildTimes;
                LinearLayoutManager layoutManager = ((LinearLayoutManager) colonsShow.getLayoutManager());
                colonsShow.smoothScrollToPosition(layoutManager.findLastVisibleItemPosition() + 100);
                Handler handler = new Handler();
                Runnable runnable = () -> {
                    LinearLayoutManager layoutManager1 = ((LinearLayoutManager) colonsShow.getLayoutManager());
                    final int vs = layoutManager1.findLastVisibleItemPosition() + 5;
                    colonsShow.smoothScrollToPosition(vs);
                    WorkedColons = false;
                };
                handler.postDelayed(runnable, tempTime);
            }
            return true;
        }
    }

    public static BuilderPlay builder(Activity activity) {
        return new ColonsBilderShow().new BuilderPlay(activity);
    }

    public class BuilderPlay {
        private BuilderPlay(Activity activity) {
            ColonsBilderShow.this.NainACtivity = activity;
            ColonsBilderShow.this.colonsShows = new ArrayList<>();
            ColonsBilderShow.this.GiveDrawablesId = new ArrayList<>();
            ColonsBilderShow.this.LinerlayoutManagers = new ArrayList<>();
            ColonsBilderShow.this.WorkedColons = false;
        }

        public BuilderPlay addSlots(Integer... slotsViewId) {
            ColonsBilderShow.this.AddAllSlots(slotsViewId);
            return this;
        }

        public BuilderPlay addDrawables(Integer... drawableIds) {
            ColonsBilderShow.this.addDrawablesPictures(drawableIds);
            return this;
        }

        public BuilderPlay setScrollTimePerInch(Float scrollTimePerInch) {
            ColonsBilderShow.this.ScrollingTimes = scrollTimePerInch;
            return this;
        }

        public BuilderPlay setDockingTimePerInch(Float dockingTimePerInch) {
            ColonsBilderShow.this.TimePerInch = dockingTimePerInch;
            return this;
        }

        public BuilderPlay setScrollTime(Integer scrollTime) {
            ColonsBilderShow.this.TimeColonsGo = scrollTime;
            return this;
        }

        public BuilderPlay setChildIncTime(Integer childIncTime) {
            ColonsBilderShow.this.ChildTimes = childIncTime;
            return this;
        }

        public BuilderPlay setOnFinishListener(CalallBack calallBack) {
            ColonsBilderShow.this.calallBack = calallBack;
            return this;
        }

        public ColonsBilderShow build() {
            ColonsBilderShow.this.buildColons();
            return ColonsBilderShow.this;
        }

    }
}
