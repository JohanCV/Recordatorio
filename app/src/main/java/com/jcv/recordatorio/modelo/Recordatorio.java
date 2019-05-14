package com.jcv.recordatorio.modelo;

import com.jcv.recordatorio.Inicio;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Recordatorio extends RealmObject {

    @PrimaryKey
    private int id;
    private String nombre;
    private Date fecha;

    //Para relacionar con otros modelos
    private RealmList<Tarea> listaTareas;

    //Real necesita un contructor vacio
    public Recordatorio() {

    }

    public Recordatorio(String nombre) {
        this.id = Inicio.recordatorioId.incrementAndGet();
        this.nombre = nombre;
        //this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public RealmList<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(RealmList<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }
}
