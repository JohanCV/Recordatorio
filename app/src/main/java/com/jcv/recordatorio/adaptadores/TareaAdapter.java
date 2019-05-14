package com.jcv.recordatorio.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jcv.recordatorio.R;
import com.jcv.recordatorio.modelo.Tarea;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.ViewHolderTarea> {
    private Context contexto;
    private List<Tarea> listTarea;

    public TareaAdapter(Context contexto, List<Tarea> listTarea) {
        this.contexto = contexto;
        this.listTarea = listTarea;
    }

    @NonNull
    @Override
    public ViewHolderTarea onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_tarea,viewGroup,false);

        return new ViewHolderTarea(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTarea viewHolderTarea, int i) {
        viewHolderTarea.tarea.setText(listTarea.get(i).getTarea());
        viewHolderTarea.responsable.setText(listTarea.get(i).getResponsable());

        @SuppressLint("SimpleDateFormat")DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(listTarea.get(i).getFecha());
        viewHolderTarea.fecha.setText(fecha);

        viewHolderTarea.estado.setChecked(listTarea.get(i).getEstado());
    }

    @Override
    public int getItemCount() {
        return listTarea.size();
    }

    public class ViewHolderTarea extends RecyclerView.ViewHolder {

        TextView tarea, responsable, fecha;
        CheckBox estado;

        public ViewHolderTarea(@NonNull View itemView) {
            super(itemView);

            tarea = itemView.findViewById(R.id.txtItemTarea);
            responsable = itemView.findViewById(R.id.txtItemResponsable);
            fecha = itemView.findViewById(R.id.txtItemFecha);
            estado = itemView.findViewById(R.id.checkBox);
        }
    }
}
