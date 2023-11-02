package org.example;

public class persona {
    private String DNI;
    private String nom;
    private String cognom;

    public persona(String DNI, String nom, String cognom) {
        this.DNI = DNI;
        this.nom = nom;
        this.cognom = cognom;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getDNI() {
        return DNI;
    }

    public String getNom() {
        return nom;
    }

    public String getCognom() {
        return cognom;
    }

    @Override
    public String toString() {
        return "\nDNI:        " + DNI +
                "\nNom:       " + nom +
                "\nCognom:    " + cognom;
    }
}
