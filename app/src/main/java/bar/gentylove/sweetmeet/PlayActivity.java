package bar.gentylove.sweetmeet;

import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayActivity extends AppCompatActivity {

    private static final String Zaymy = "zayme";
    private static final String BetZaumy = "BetZaume";
    private int TotalOchi;
    private int TotalOch2;
    private int DefaultIntIndex;


    private TextView ZaymeView;

    private Button TotalSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TotalSpin = findViewById(R.id.button_crutit);
        ZaymeView = findViewById(R.id.totalscores);



        if (savedInstanceState != null) {
          TotalOch2 = 500;


        }else { TotalOchi = 500; setScoreAmounts();}

        setupSlotsMachine();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Zaymy, TotalOchi);
        outState.putInt(BetZaumy, DefaultIntIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TotalOchi = savedInstanceState.getInt(Zaymy);
        DefaultIntIndex = savedInstanceState.getInt(BetZaumy);
        setScoreAmounts();
    }

    private void ScoresTotal() {
        TotalOchi -= 10;
        if (9 > TotalOchi) DefaultIntIndex = 0;
        setScoreAmounts();
        if (TotalOchi == 0) {
            TotalSpin.setEnabled(false);
        }

    }



    private void setScoreAmounts() {
        ZaymeView.setText(String.valueOf(TotalOchi));

    }

    private void setupSlotsMachine() {
        final ColonsBilderShow.BuilderPlay builderPlay = ColonsBilderShow.builder(this);
        final ColonsBilderShow slots = builderPlay
                .addSlots(R.id.firstcolomn, R.id.secondcolomn, R.id.threecolomn, R.id.fourcolomn, R.id.fivecolomn)
                .addDrawables(R.drawable.slot_colomn_1, R.drawable.slot_colomn_2, R.drawable.slot_colomn_3, R.drawable.slot_colomn_4)
                .setScrollTimePerInch(0.5f)
                .setDockingTimePerInch(0.3f)
                .setScrollTime(200 + new SecureRandom().nextInt(1200))
                .setChildIncTime(800)
                .setOnFinishListener(new CalallBack() {
                    @Override
                    public void OnFinishListener() {

                        List<LinearLayoutManager> layoutManagers = getLinerLayoutManagers();


                        Map<Integer, Integer> match = new HashMap<>(4);

                        for (int i = 0; i < 4; i++) {
                            ImageView imageView = (ImageView) layoutManagers.get(i)
                                    .findViewByPosition(layoutManagers.get(i)
                                            .findFirstVisibleItemPosition() + 2);
                            Integer drawableId = (Integer) imageView.getTag();

                            if (match.containsKey(drawableId)) {
                                match.put(drawableId, match.get(drawableId) + 2);
                            } else {
                                match.put(drawableId, 2);
                            }
                        }

                        int resultMatch = 0;
                        for (Integer val : match.values()) {
                            if (resultMatch < val) {
                                resultMatch = val;
                            }
                        }

                        if (resultMatch >= 3 && !TotalSpin.isEnabled()) {
                            TotalSpin.setEnabled(true);
                        }

                        switch (resultMatch) {
                            case 4:
                                TotalOchi += 11 * 2;
                                break;
                            case 3:
                                TotalOchi += 11 * 3;
                                break;
                            case 5:
                                TotalOchi += 11 * 4;
                                break;
                            default:
                                if (TotalOchi <= 0) {
                                    Toast.makeText(PlayActivity.this,"you losser",Toast.LENGTH_SHORT).show();
                                }
                        }
                        setScoreAmounts();

                    }
                })
                .build();



        TotalSpin.setOnClickListener(v -> {
            ScoresTotal();
            slots.startPlay();
        });
    }




}
