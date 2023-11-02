package org.example;

import java.io.Serializable;
import java.util.ArrayList;

public class impartir implements Serializable {
    private String nomAsignatura;
    private String dniProfessor;
    private String dniAlumne;
    private ArrayList<String> activitatsNomNotes, examenNota;

    public impartir(String nomAsignatura, String dniProfessor, String dniAlumne, ArrayList<String> activitatsNomNotes, ArrayList<String> examenNota) {
        this.nomAsignatura = nomAsignatura;
        this.dniProfessor = dniProfessor;
        this.dniAlumne = dniAlumne;
        this.activitatsNomNotes = activitatsNomNotes;
        this.examenNota = examenNota;
    }

    public void setNomAsignatura(String nomAsignatura) {
        this.nomAsignatura = nomAsignatura;
    }

    public void setDniProfessor(String dniProfessor) {
        this.dniProfessor = dniProfessor;
    }

    public void setDniAlumne(String dniAlumne) {
        this.dniAlumne = dniAlumne;
    }

    public void setActivitatsNomNotes(ArrayList<String> activitatsNomNotes) {
        this.activitatsNomNotes = activitatsNomNotes;
    }

    public void setExamenNota(ArrayList<String> examenNota) {
        this.examenNota = examenNota;
    }

    public String getNomAsignatura() {
        return nomAsignatura;
    }

    public String getDniProfessor() {
        return dniProfessor;
    }

    public String getDniAlumne() {
        return dniAlumne;
    }

    public ArrayList<String> getActivitatsNomNotes() {
        return activitatsNomNotes;
    }

    public ArrayList<String> getExamenNota() {
        return examenNota;
    }

    @Override
    public String toString() {
        return "\nNom Asignatura: " + nomAsignatura +
                "\nDNI Professor: " + dniProfessor +
                "\nDNI Alumne: " + dniAlumne +
                "\nActivitats Nom Notes: " + activitatsNomNotes +
                "\nExamen Nota: " + examenNota;
    }
}
