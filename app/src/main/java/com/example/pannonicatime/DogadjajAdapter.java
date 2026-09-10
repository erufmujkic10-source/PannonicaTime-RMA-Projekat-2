package com.example.pannonicatime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DogadjajAdapter extends RecyclerView.Adapter<DogadjajAdapter.DogadjajViewHolder> {

    private List<DogadjajStavka> lista;
    private Context context;

    public DogadjajAdapter(List<DogadjajStavka> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public DogadjajViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dogadjaj, parent, false);
        return new DogadjajViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogadjajViewHolder holder, int position) {
        DogadjajStavka stavka = lista.get(position);
        holder.tvDan.setText(stavka.getDan());
        holder.tvNaziv.setText(stavka.getNaziv());
        holder.tvVrijeme.setText(stavka.getVrijeme());
        holder.tvOpis.setText(stavka.getOpis());
        holder.ivIkona.setImageResource(stavka.getIkonaResurs());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class DogadjajViewHolder extends RecyclerView.ViewHolder {
        TextView tvDan, tvNaziv, tvVrijeme, tvOpis;
        ImageView ivIkona;

        public DogadjajViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDan = itemView.findViewById(R.id.tvDogadjajDan);
            tvNaziv = itemView.findViewById(R.id.tvDogadjajNaziv);
            tvVrijeme = itemView.findViewById(R.id.tvDogadjajVrijeme);
            tvOpis = itemView.findViewById(R.id.tvDogadjajOpis);
            ivIkona = itemView.findViewById(R.id.ivDogadjajIkona);
        }
    }
}