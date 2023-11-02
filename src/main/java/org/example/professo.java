package org.example;

public class professo extends persona{
    private String seguretatSocial;

    public professo(String DNI, String nom, String cognom, String seguretatSocial) {
        super(DNI, nom, cognom);
        this.seguretatSocial = seguretatSocial;
    }

    public String getSeguretatSocial() {
        return seguretatSocial;
    }

    public void setSeguretatSocial(String seguretatSocial) {
        this.seguretatSocial = seguretatSocial;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nSeguretat Social:    " + seguretatSocial;
    }
}
