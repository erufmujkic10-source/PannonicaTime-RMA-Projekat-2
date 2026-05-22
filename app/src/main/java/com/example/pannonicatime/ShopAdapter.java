package com.example.pannonicatime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<SearchStavka> artikli;
    private Context context;
    private OnKolicinaChangedListener listener;

    public interface OnKolicinaChangedListener {
        void onKolicinaMijenjana();
    }

    public ShopAdapter(List<SearchStavka> artikli, Context context, OnKolicinaChangedListener listener) {
        this.artikli = artikli;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_artikal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchStavka artikal = artikli.get(position);

        holder.tvNaslov.setText(artikal.getNaslov());
        holder.tvOpis.setText(artikal.getOpis());
        holder.tvCijena.setText(String.format("%.2f KM", artikal.getCijenaNumericki()));
        holder.ivSlika.setImageResource(artikal.getSlikaResId());
        holder.tvKolicina.setText(String.valueOf(artikal.getKolicina()));

        holder.btnPlus.setOnClickListener(v -> {
            artikal.setKolicina(artikal.getKolicina() + 1);
            holder.tvKolicina.setText(String.valueOf(artikal.getKolicina()));
            if (listener != null) listener.onKolicinaMijenjana();
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (artikal.getKolicina() > 0) {
                artikal.setKolicina(artikal.getKolicina() - 1);
                holder.tvKolicina.setText(String.valueOf(artikal.getKolicina()));
                if (listener != null) listener.onKolicinaMijenjana();
            }
        });
    }

    @Override
    public int getItemCount() {
        return artikli.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSlika;
        TextView tvNaslov, tvOpis, tvCijena, tvKolicina;
        Button btnPlus, btnMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSlika = itemView.findViewById(R.id.ivArtikalSlika);
            tvNaslov = itemView.findViewById(R.id.tvArtikalNaslov);
            tvOpis = itemView.findViewById(R.id.tvArtikalOpis);
            tvCijena = itemView.findViewById(R.id.tvArtikalCijena);
            tvKolicina = itemView.findViewById(R.id.tvKolicina);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}