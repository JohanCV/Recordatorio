package com.jcv.recordatorio.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcv.recordatorio.R;
import com.jcv.recordatorio.modelo.Recordatorio;
import com.jcv.recordatorio.vista.TareasView;

import java.util.List;

public class RecordatorioAdapter extends RecyclerView.Adapter<RecordatorioAdapter.ViewHolderRecordatorio>
        implements View.OnClickListener, View.OnLongClickListener{

    private Context context;
    private List<Recordatorio> listaRecordatorios;
    private View.OnClickListener listener;
    private View.OnLongClickListener onLongClickListener;

    public RecordatorioAdapter(Context context, List<Recordatorio> listaRecordatorios){
        this.context = context;
        this.listaRecordatorios = listaRecordatorios;
    }

    @NonNull
    @Override
    public ViewHolderRecordatorio onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_recordatorio,viewGroup,false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolderRecordatorio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecordatorio viewHolderRecordatorio, int i) {
        viewHolderRecordatorio.recordatorio.setText(listaRecordatorios.get(i).getNombre());
        viewHolderRecordatorio.fecha.setText((CharSequence) listaRecordatorios.get(i).getFecha());
        viewHolderRecordatorio.fabListaTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, TareasView.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaRecordatorios.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if (listener!= null)
            listener.onClick(view);
    }
    public void setOnLongClickListener(View.OnLongClickListener listener){
        this.onLongClickListener = listener;
    }
    @Override
    public boolean onLongClick(View view) {
        if (onLongClickListener != null){
            onLongClickListener.onLongClick(view);
        }
        return false;
    }

    public class ViewHolderRecordatorio extends RecyclerView.ViewHolder{

        TextView recordatorio, fecha;
        FloatingActionButton fabListaTarea;

        public ViewHolderRecordatorio(@NonNull View itemView) {
            super(itemView);

            recordatorio = itemView.findViewById(R.id.txtItemRecordatorio);
            fecha = itemView.findViewById(R.id.txtItemFecha);
            fabListaTarea = itemView.findViewById(R.id.fabListaTareasRecordatorio);
        }
    }
}
