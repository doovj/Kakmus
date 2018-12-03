package com.doovj.kakmus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusHolder> {
    private ArrayList<KamusModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public KamusAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public KamusHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_kata_row, viewGroup, false);
        return new KamusHolder(view);
    }

    public void addItem(ArrayList<KamusModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(KamusHolder kamusHolder, int i) {
        kamusHolder.textViewKata.setText(mData.get(i).getKata());
        kamusHolder.textViewArti.setText(mData.get(i).getArti());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class KamusHolder extends RecyclerView.ViewHolder {
        private TextView textViewKata;
        private TextView textViewArti;

        public KamusHolder(View itemView) {
            super(itemView);

            textViewKata = (TextView)itemView.findViewById(R.id.tv_kata);
            textViewArti = (TextView)itemView.findViewById(R.id.tv_arti);
        }
    }
}
