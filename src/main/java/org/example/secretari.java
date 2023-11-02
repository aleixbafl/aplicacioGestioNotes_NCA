package org.example;

public class secretari extends persona{
    private String seguretatSocial;

    public secretari(String DNI, String nom, String cognom, String seguretatSocial) {
        super(DNI, nom, cognom);
        this.seguretatSocial = seguretatSocial;
    }

    public void setSeguretatSocial(String seguretatSocial) {
        this.seguretatSocial = seguretatSocial;
    }

    public String getSeguretatSocial() {
        return seguretatSocial;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nSeguretat Social:    " + seguretatSocial;
    }
}
