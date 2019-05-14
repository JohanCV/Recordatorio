package com.jcv.recordatorio.vista;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jcv.recordatorio.R;
import com.jcv.recordatorio.adaptadores.RecordatorioAdapter;
import com.jcv.recordatorio.modelo.Recordatorio;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RecordatoriosView extends AppCompatActivity implements RealmChangeListener<RealmResults<Recordatorio>> {

    public Realm realm;
    private FloatingActionButton fabNuevoRecordatorio;
    private RealmResults<Recordatorio> listaRecordatorios;
    private RecyclerView recyclerViewRecordatorios;
    private RecordatorioAdapter recordatorioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios_view);

        realm = Realm.getDefaultInstance();
        findViewID();
    }

    private void findViewID() {
        fabNuevoRecordatorio = findViewById(R.id.fabNuevoRecordatorio);

        listaRecordatorios = realm.where(Recordatorio.class).findAll();
        listaRecordatorios.addChangeListener(this);

        recyclerViewRecordatorios = findViewById(R.id.recyclerRecordatorio);
        recyclerViewRecordatorios.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recordatorioAdapter = new RecordatorioAdapter(getApplicationContext(),listaRecordatorios);
        recyclerViewRecordatorios.setAdapter(recordatorioAdapter);

        listenOnClick();
    }

    private void listenOnClick() {
        fabNuevoRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertNuevoRecordatorio();
            }
        });
        recordatorioAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recordatorio recordatori = listaRecordatorios.get(recyclerViewRecordatorios.getChildAdapterPosition(view));
                alertEditarRecordatorio(recordatori);
            }
        });
        recordatorioAdapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Recordatorio recordatori = listaRecordatorios.get(recyclerViewRecordatorios.getChildAdapterPosition(view));
                alertEliminarRecordatorio(view);
                return false;
            }
        });
    }

    public void alertNuevoRecordatorio(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordatoriosView.this);
        final View view = LayoutInflater.from(RecordatoriosView.this).inflate(R.layout.dialogo_nuevo_recordatorio,null);
        builder.setView(view);

        final EditText editRecordatorio = view.findViewById(R.id.etxtRecord);
        final EditText editfecha = view.findViewById(R.id.etxtFechaRecord);

        //builder.setMessage(getString(R.string.mensaje_alerta));
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String recordatorio = editRecordatorio.getText().toString().trim();
                String fecha =  editfecha.getText().toString().trim();
                if (recordatorio.length() >0){
                    guardarRecordatorio(recordatorio, fecha);
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_mensaje_vacio), Toast.LENGTH_SHORT).show();
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

    public void alertEditarRecordatorio(final Recordatorio record){
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordatoriosView.this);
        final View vi = LayoutInflater.from(RecordatoriosView.this).inflate(R.layout.dialogo_editar_recordatorio,null);
        builder.setView(vi);

        final EditText editTextRecordatorio = vi.findViewById(R.id.editartxtRecord);
        final EditText editTextFecha =  vi.findViewById(R.id.editartxtFechaRecord);

        editTextRecordatorio.setText(record.getNombre());
        editTextFecha.setText((CharSequence) record.getFecha());

        //posicionamos el cursor al final
        editTextRecordatorio.setSelection(editTextRecordatorio.getText().length());

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nombreRecordatorio = editTextRecordatorio.getText().toString().trim();
                String fechaRecordatorio = editTextFecha.getText().toString().trim();
                if (nombreRecordatorio.length() >0){
                    editarRecordatorio(nombreRecordatorio, fechaRecordatorio,record);
                }else {
                    Toast.makeText(getApplicationContext(), R.string.toast_mensaje_vacio, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog actualizarAlert = builder.create();
        actualizarAlert.show();
    }

    public void alertEliminarRecordatorio(final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordatoriosView.this);
        final View vista = LayoutInflater.from(RecordatoriosView.this).inflate(R.layout.dialogo_eliminar_recordatorio,null);
        builder.setView(vista);

        //builder.setMessage(getString(R.string.elimiar));
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                elimarRecordatorio(view);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog eliminarAlert = builder.create();
        eliminarAlert.show();
    }

    public void elimarRecordatorio(View view){
        Recordatorio recordatorio = listaRecordatorios.get(recyclerViewRecordatorios.getChildAdapterPosition(view));
        realm.beginTransaction();
        assert recordatorio != null;
        recordatorio.deleteFromRealm();
        realm.commitTransaction();
    }

    public void guardarRecordatorio(String recordatori, String fecha){
        realm.beginTransaction();
        Recordatorio recordatorio = new Recordatorio(recordatori);
        realm.copyToRealm(recordatorio);
        realm.commitTransaction();
    }
    public void editarRecordatorio(String recordatorio, String fecha, Recordatorio record){
        realm.beginTransaction();
        record.setNombre(recordatorio);
        //record.setFecha(fecha);
        realm.commitTransaction();
    }
    @Override
    public void onChange(RealmResults<Recordatorio> recordatorios) {
        recordatorioAdapter.notifyDataSetChanged();
    }
}
