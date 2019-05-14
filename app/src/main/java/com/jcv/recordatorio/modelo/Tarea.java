package com.jcv.recordatorio.modelo;

import com.jcv.recordatorio.Inicio;

import java.util.Date;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tarea extends RealmObject {

    @PrimaryKey
    private int id;
    private String responsable;
    private String tarea;
    private boolean estado;
    private Date fecha;

    private RealmList<Recordatorio> listaRecordatorioPadre;

    //Real necesita un contructor vacio
    public Tarea() {
    }

    public Tarea(String responsable, String tarea, Date fecha) {
        this.id = Inicio.tareasId.incrementAndGet();
        this.responsable = responsable;
        this.tarea = tarea;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public RealmList<Recordatorio> getListaRecordatorioPadre() {
        return listaRecordatorioPadre;
    }

    public void setListaRecordatorioPadre(RealmList<Recordatorio> listaRecordatorioPadre) {
        this.listaRecordatorioPadre = listaRecordatorioPadre;
    }
}
