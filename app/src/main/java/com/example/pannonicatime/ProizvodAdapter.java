package com.example.pannonicatime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pannonicatime.model.Proizvod;
import java.util.List;

public class ProizvodAdapter extends RecyclerView.Adapter<ProizvodAdapter.ProizvodViewHolder> {

    private List<Proizvod> listaProizvoda;
    private OnKupovinaClickListener clickListener;


    public interface OnKupovinaClickListener {
        void onKupiClick(Proizvod proizvod);
    }

    public ProizvodAdapter(List<Proizvod> listaProizvoda, OnKupovinaClickListener clickListener) {
        this.listaProizvoda = listaProizvoda;
        this.clickListener = clickListener;
    }

    public void postaviNovuListu(List<Proizvod> novaLista) {
        this.listaProizvoda = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProizvodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proizvod, parent, false);
        return new ProizvodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProizvodViewHolder holder, int position) {
        Proizvod p = listaProizvoda.get(position);
        holder.tvNaziv.setText(p.getNaziv());
        holder.tvOpis.setText(p.getOpis());
        holder.tvCijena.setText(String.format("%.2f KM", p.getCijena()));

        holder.btnKupi.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onKupiClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProizvoda.size();
    }

    public static class ProizvodViewHolder extends RecyclerView.ViewHolder {
        TextView tvNaziv, tvOpis, tvCijena;
        Button btnKupi;

        public ProizvodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNaziv = itemView.findViewById(R.id.tvNazivProizvoda);
            tvOpis = itemView.findViewById(R.id.tvOpisProizvoda);
            tvCijena = itemView.findViewById(R.id.tvCijenaProizvoda);
            btnKupi = itemView.findViewById(R.id.btnKupi);
        }
    }
}