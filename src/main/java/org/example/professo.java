package org.example;

public class professo extends persona{
    private int seguretatSocial;

    public professo(String DNI, String nom, String cognom, int seguretatSocial) {
        super(DNI, nom, cognom);
        this.seguretatSocial = seguretatSocial;
    }

    public int getSeguretatSocial() {
        return seguretatSocial;
    }

    public void setSeguretatSocial(int seguretatSocial) {
        this.seguretatSocial = seguretatSocial;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nSeguretat Social:    " + seguretatSocial;
    }
}
