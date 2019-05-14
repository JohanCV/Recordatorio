package com.jcv.recordatorio.vista;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jcv.recordatorio.R;
import com.jcv.recordatorio.adaptadores.TareaAdapter;
import com.jcv.recordatorio.modelo.Tarea;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class TareasView extends AppCompatActivity implements RealmChangeListener<RealmResults<Tarea>> {

    public Realm realm;
    private FloatingActionButton fabNuevaTarea;
    private RealmResults<Tarea> listaTareas;
    private RecyclerView recyclerViewTarea;
    private TareaAdapter tareaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_view);
        setTitle("Tarea");
        realm = Realm.getDefaultInstance();
        findViewId();
    }

    private void findViewId() {
        fabNuevaTarea = findViewById(R.id.fabNuevaTarea);

        listaTareas = realm.where(Tarea.class).findAll();
        listaTareas.addChangeListener(this);

        recyclerViewTarea = findViewById(R.id.recyclerTareas);
        recyclerViewTarea.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        tareaAdapter = new TareaAdapter(getApplicationContext(),listaTareas);
        recyclerViewTarea.setAdapter(tareaAdapter);

        listenOnClick();
    }

    private void listenOnClick() {
        fabNuevaTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertNuevaTarea();
            }
        });
    }

    public void alertNuevaTarea(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TareasView.this);
        final View view = LayoutInflater.from(TareasView.this).inflate(R.layout.dialogo_nuevo_tarea,null);
        builder.setView(view);

        final EditText editTextTarea = view.findViewById(R.id.etxtTarea);
        final EditText editTextResponsable = view.findViewById(R.id.etxtResponsableTarea);
        final EditText dateFecha = view.findViewById(R.id.etxtFechaTarea);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tarea = editTextTarea.getText().toString().trim();
                String responsable = editTextResponsable.getText().toString().trim();
                String date = dateFecha.getText().toString().trim();

                if (tarea.length()> 0){
                    try {
                        guardarTarea(tarea,responsable,date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), R.string.toast_mensaje_vacio, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void guardarTarea(String tarea, String responsable, String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date1 = dateFormat.parse(date);
        realm.beginTransaction();
        Tarea tareaNueva = new Tarea(tarea, responsable, date1);
        realm.copyToRealm(tareaNueva);
        realm.commitTransaction();
    }

    @Override
    public void onChange(RealmResults<Tarea> tareas) {
        tareaAdapter.notifyDataSetChanged();
    }
}
