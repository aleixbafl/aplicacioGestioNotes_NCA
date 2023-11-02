package org.example;

import java.io.Serializable;

public class loginUsuaris implements Serializable {
    private String dni;
    private String contrasenya;
    private boolean secretati;

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public void setSecretati(boolean secretati) {
        this.secretati = secretati;
    }

    public String getDni() {
        return dni;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public boolean isSecretati() {
        return secretati;
    }

    public loginUsuaris(String dni, String contrasenya, boolean secretati) {
        this.dni = dni;
        this.contrasenya = contrasenya;
        this.secretati = secretati;
    }
}
