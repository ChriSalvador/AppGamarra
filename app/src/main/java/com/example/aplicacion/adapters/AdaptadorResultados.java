package com.example.aplicacion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.R;
import com.example.aplicacion.entidades.Resultados;

import java.util.List;

public class AdaptadorResultados extends RecyclerView.Adapter<AdaptadorResultados.ViewHolderTienda> {

    List<Resultados> listaResultados;
    View vista;

    public AdaptadorResultados(List<Resultados> listaResultados) {
        this.listaResultados = listaResultados;
    }

    @NonNull
    @Override
    public ViewHolderTienda onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_resultados, viewGroup, false);
        return new ViewHolderTienda(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTienda viewHolderTienda, int i) {
        viewHolderTienda.txtRazon.setText(listaResultados.get(i).getRazonSoc());
        viewHolderTienda.txtDireccion.setText(listaResultados.get(i).getDireccion());
        viewHolderTienda.txtReferencia.setText(listaResultados.get(i).getReferencia());
        viewHolderTienda.txtPrenda.setText(listaResultados.get(i).getPrenda());
        viewHolderTienda.txtPublico.setText(listaResultados.get(i).getPublico());
        viewHolderTienda.txtZona.setText(listaResultados.get(i).getZona());
    }

    @Override
    public int getItemCount() {
        return listaResultados.size();
    }

    public class ViewHolderTienda extends RecyclerView.ViewHolder {

        ImageView imgTienda;
        TextView txtRazon;
        TextView txtDireccion;
        TextView txtReferencia;
        TextView txtPrenda;
        TextView txtPublico;
        TextView txtZona;

        public ViewHolderTienda(@NonNull View itemView) {
            super(itemView);
            imgTienda = itemView.findViewById(R.id.idImagen);
            txtRazon = itemView.findViewById(R.id.idRaz);
            txtDireccion = itemView.findViewById(R.id.idDirecc);
            txtReferencia = itemView.findViewById(R.id.idRefe);
            txtPrenda = itemView.findViewById(R.id.idVentPr);
            txtPublico = itemView.findViewById(R.id.idPubDir);
            txtZona = itemView.findViewById(R.id.idZon);
        }
    }
}
