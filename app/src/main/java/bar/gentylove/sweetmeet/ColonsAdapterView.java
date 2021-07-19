package bar.gentylove.sweetmeet;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColonsAdapterView extends RecyclerView.Adapter<ColonsAdapterView.ViewHolder> {
    private List<Integer> scroresAll;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ImageView;

        public ViewHolder(View v) {
            super(v);
            ImageView = v.findViewById(R.id.totalscore);
        }
    }

    public ColonsAdapterView(List<Integer> myDataset) {
        scroresAll = new ArrayList<>(myDataset);
        Collections.shuffle(scroresAll);
    }

    @NonNull
    @Override
    public ColonsAdapterView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int IndexScrores = scroresAll.get(position % scroresAll.size());
        holder.ImageView.setImageResource(IndexScrores);
        holder.ImageView.setTag(IndexScrores);


    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}