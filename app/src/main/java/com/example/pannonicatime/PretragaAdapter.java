package com.example.pannonicatime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PretragaAdapter extends RecyclerView.Adapter<PretragaAdapter.PretragaViewHolder> {

    private List<SearchStavka> listaStavki;
    private Context context;

    public PretragaAdapter(List<SearchStavka> listaStavki, Context context) {
        this.listaStavki = listaStavki;
        this.context = context;
    }

    private int getThemeColor(int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    @NonNull
    @Override
    public PretragaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pretraga, parent, false);
        return new PretragaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PretragaViewHolder holder, int position) {
        SearchStavka stavka = listaStavki.get(position);

        holder.tvNaslov.setText(stavka.getNaslov());
        holder.tvOpis.setText(stavka.getOpis());
        holder.ivSlika.setImageResource(stavka.getSlikaResId());

        if (stavka.getCijenaNumericki() == 0) {
            holder.tvCijena.setText("Atrakcija kompleksa");
            holder.tvCijena.setTextColor(Color.parseColor("#059669"));
        } else {
            holder.tvCijena.setText(String.format("%.2f KM", stavka.getCijenaNumericki()));
            holder.tvCijena.setTextColor(getThemeColor(com.google.android.material.R.attr.colorPrimary));
        }

        holder.itemView.setOnClickListener(v -> {
            if (stavka.getTip().equalsIgnoreCase("atrakcija")) {
                prikaziDetaljeAtrakcije(stavka);
            } else {
                Intent intent = new Intent(context, ShopActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaStavki.size();
    }

    private void prikaziDetaljeAtrakcije(SearchStavka stavka) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dijalogView = LayoutInflater.from(context).inflate(R.layout.dialog_detalji, null);

        TextView tvNaslov = dijalogView.findViewById(R.id.tvDijalogNaslov);
        ImageView ivSlika = dijalogView.findViewById(R.id.ivDijalogSlika);
        TextView tvOpis = dijalogView.findViewById(R.id.tvDijalogOpis);
        Button btnZatvori = dijalogView.findViewById(R.id.btnDijalogZatvori);

        if (tvNaslov != null) tvNaslov.setText(stavka.getNaslov());
        if (ivSlika != null) ivSlika.setImageResource(stavka.getSlikaResId());
        if (tvOpis != null) tvOpis.setText(stavka.getOpis());

        builder.setView(dijalogView);
        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        if (btnZatvori != null) {
            btnZatvori.setOnClickListener(v -> dialog.dismiss());
        }
        dialog.show();
    }

    static class PretragaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSlika;
        TextView tvNaslov, tvOpis, tvCijena;

        public PretragaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSlika = itemView.findViewById(R.id.ivStavkaSlika);
            tvNaslov = itemView.findViewById(R.id.tvStavkaNaslov);
            tvOpis = itemView.findViewById(R.id.tvStavkaOpis);
            tvCijena = itemView.findViewById(R.id.tvStavkaCijena);
        }
    }
}