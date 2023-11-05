package org.example;

import java.io.Serializable;

public class alumne extends persona implements Serializable {
    private String expedient;
    private String matricula;

    public alumne(String DNI, String nom, String cognom, String expedient, String matricula) {
        super(DNI, nom, cognom);
        this.expedient = expedient;
        this.matricula = matricula;
    }

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nExpedient:" + expedient +
                "\mMatricula='" + matricula;
    }
}
