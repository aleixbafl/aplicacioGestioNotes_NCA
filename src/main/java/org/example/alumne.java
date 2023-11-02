package org.example;

public class alumne extends persona{
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

    @Override
    public String toString() {
        return super.toString() +
                "\nSeguretat Social:    " + expedient;
    }
}
