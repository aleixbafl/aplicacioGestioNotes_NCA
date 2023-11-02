package org.example;

import java.io.Serializable;

public class curs implements Serializable {
    private String nom;
    private boolean finalitzat;
    private float notaFinal;

    @Override
    public String toString() {
        return "\nNom: " + nom +
                "\nFinalitzat=" + finalitzat +
                "\nNota Final: " + notaFinal;
    }

    public curs(String nom, boolean finalitzat, float notaFinal) {
        this.nom = nom;
        this.finalitzat = finalitzat;
        this.notaFinal = notaFinal;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setFinalitzat(boolean finalitzat) {
        this.finalitzat = finalitzat;
    }

    public void setNotaFinal(float notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getNom() {
        return nom;
    }

    public boolean isFinalitzat() {
        return finalitzat;
    }

    public float getNotaFinal() {
        return notaFinal;
    }
}
