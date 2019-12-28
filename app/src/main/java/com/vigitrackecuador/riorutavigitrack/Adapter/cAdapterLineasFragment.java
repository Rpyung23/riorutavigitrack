package com.vigitrackecuador.riorutavigitrack.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vigitrackecuador.riorutavigitrack.Interface.RecyclerViewOnItemClickListener;
import com.vigitrackecuador.riorutavigitrack.POO.cBuses;
import com.vigitrackecuador.riorutavigitrack.R;

import java.util.ArrayList;

public class cAdapterLineasFragment extends RecyclerView.Adapter<cAdapterLineasFragment.viewHolderLineas>
{
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    ArrayList<cBuses>oB;
    int vista;
    Activity activity;

    public cAdapterLineasFragment(RecyclerViewOnItemClickListener recyclerViewOnItemClickListener, ArrayList<cBuses> oB, int vista, Activity activity) {
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        this.oB = oB;
        this.vista = vista;
        this.activity = activity;
    }

    @NonNull
    @Override
    public viewHolderLineas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(vista,parent,false);
        return new viewHolderLineas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderLineas holder, int position)
    {
        cBuses oBB =  oB.get(position);
        holder.linea.setText(oBB.getLinea_bus());
        holder.hora_I.setText("Hora Inicial : "+oBB.getHora_inicio_bus());
        holder.hora_F.setText("Hora Final : "+oBB.getHora_final_bus());
        holder.frecuenia.setText("Frecuencia : "+oBB.getFrecuencia());

    }

    @Override
    public int getItemCount() {
        return oB.size();
    }
    class viewHolderLineas extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView frecuenia,linea,hora_I,hora_F;

        public viewHolderLineas(@NonNull View itemView) {
            super(itemView);
            frecuenia=itemView.findViewById(R.id.text_frecuencia_linea_bus);
            linea=itemView.findViewById(R.id.text_linea_bus);
            hora_F=itemView.findViewById(R.id.text_hora_final_linea_bus);
            hora_I=itemView.findViewById(R.id.text_hora_inicio_linea_bus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            recyclerViewOnItemClickListener.onClick(itemView, getAdapterPosition());
        }

    }
}
