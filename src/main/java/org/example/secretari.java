package org.example;

public class secretari extends persona{
    private int seguretatSocial;

    public secretari(String DNI, String nom, String cognom, int seguretatSocial) {
        super(DNI, nom, cognom);
        this.seguretatSocial = seguretatSocial;
    }

    public void setSeguretatSocial(int seguretatSocial) {
        this.seguretatSocial = seguretatSocial;
    }

    public int getSeguretatSocial() {
        return seguretatSocial;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nSeguretat Social:    " + seguretatSocial;
    }
}
