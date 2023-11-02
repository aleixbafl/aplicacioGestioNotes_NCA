package org.example;

import java.io.Serializable;

public class asignatura extends curs implements Serializable {
    private String nom, tema;
    private int hores;

    public asignatura(String nomAsignatura, boolean finalitzat, float notaFinal, String nom, String tema, int hores) {
        super(nomAsignatura, finalitzat, notaFinal);
        this.nom = nom;
        this.tema = tema;
        this.hores = hores;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setHores(int hores) {
        this.hores = hores;
    }

    @Override
    public String getNom() {
        return nom;
    }

    public String getTema() {
        return tema;
    }

    public int getHores() {
        return hores;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nNom: " + nom +
                "\nTema: " + tema +
                "\nHores: " + hores;
    }
}
