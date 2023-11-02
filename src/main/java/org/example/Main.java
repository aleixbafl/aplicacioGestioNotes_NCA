package org.example;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Main gestio = new Main();
        gestio.Notes();
    }

    private void Notes() {
        Scanner lector = new Scanner(System.in);

        File rutaTotsArxius = ubicacioArxius(lector);//Crear un objecte File amb una ruta introduïda per teclat

        confirmaRutaUsuaari(rutaTotsArxius, lector);//Preguntar a l'usuari si està segur de la ruta introduïda
        boolean secretari = false;

        String usuari = loginUsuari(lector, secretari);

    }

    private String loginUsuari(Scanner lector, boolean secretari) {
        String DNI = "", contrasenya = "";
        System.out.println("\nLogin");
        System.out.println("Introdueix el teu DNI:");
        DNI = lleguirString(lector);
        while (!validarDNI(DNI)){
            System.out.println("El DNI es incorrecte:");
            DNI = lleguirString(lector);
        }

        System.out.println("\nIntrodueix la teva contrasenya:");
        contrasenya = lleguirString(lector);

        do {

        } while (comprobarUsuari(secretari, DNI, contrasenya));

        return DNI;
    }

    private boolean comprobarUsuari(boolean secretari, String dni, String contrasenya) {
        
    }

    private boolean validarDNI(String dni) {
        dni = dni.trim().toUpperCase();
        if (dni.length() != 9) {
            return false;
        }

        //Separa els numeros de la lletra
        String numeros = dni.substring(0, 8);
        char letra = dni.charAt(8);

        //Verificar que els primers 8 diguits siguin numeros
        if (!numeros.matches("[0-9]+")) {
            return false;
        }

        //Calcular la lletra corresponent als numeros
        int numDNI = Integer.parseInt(numeros);
        String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
        char letraCalculada = letrasValidas.charAt(numDNI % 23);

        // Comparar la letra calculada con la letra proporcionada
        return letra == letraCalculada;
    }

    private String lleguirString(Scanner lector) {
        String introduirString = lector.nextLine();
        return introduirString;
    }

    private void confirmaRutaUsuaari(File rutaTotsArxius, Scanner lector) {//Mira si hi ha arxius i si són carpetes o fitxers
        File[] arxius = rutaTotsArxius.listFiles();
        if (arxius != null){
            int contFitxer = 0, contArxiu = 0;
            for (File fitxer : arxius){
                if (!fitxer.isDirectory()){
                    if (fitxer.isFile()){
                        contFitxer++;
                    }
                }else {
                    contArxiu++;
                }
            }
            //Pintar un missatge o un altre depenent de quantes carpetes i fitxes hi ha i pregunta si vol canviar la ruta
            if ((contFitxer == 0) && (contArxiu == 0)){
                System.out.println("\nEn aquesta ruta no hi ha arxius.");
                preguntarCambiarRuta(lector, rutaTotsArxius);
            } else if ((contFitxer > 0) && (contArxiu == 0)) {
                System.out.println("En aquesta ruta únicament hi ha fitxers.");
                preguntarCambiarRuta(lector, rutaTotsArxius);
            } else if ((contFitxer == 0) && (contArxiu > 0)) {
                System.out.println("En aquesta ruta únicament hi ha carpetes.");
                preguntarCambiarRuta(lector, rutaTotsArxius);
            } else if ((contFitxer > 0) && (contArxiu > 0)) {
                System.out.println("En aquesta ruta hi ha fitxers i carpetes.");
                preguntarCambiarRuta(lector, rutaTotsArxius);
            }
        }
    }

    private void preguntarCambiarRuta(Scanner lector, File rutaTotsArxius) {//Preguntar si vol canviar la ruta
        System.out.println("Vols canviar la ruta?");
        System.out.println("1. Si");
        System.out.println("2. No");
        System.out.println("\nIntrodueix l'opció:");
        int opcio = validarInt(lector);
        while ((opcio < 1) || (opcio > 2)){
            System.out.println("Ha de ser 1 o 2:");
            opcio = validarInt(lector);
        }
        if (opcio == 1){
            File novaRuta = ubicacioArxius(lector);
            rutaTotsArxius = novaRuta;
        } else {
            System.out.println("\nLa ruta segueix sent la mateixa:");
            System.out.println(rutaTotsArxius.getPath());
        }
    }

    private File ubicacioArxius(Scanner lector) {//Introduir i comprovar la ubicació dels arxius
        System.out.println("Introdueix la ruta on tens o vols guardar els arxius:");
        String lleguirRuta = lector.nextLine();
        boolean rutaCorrecte = false;
        File rutaTotsArxius = new File(lleguirRuta);
        do {
            if (rutaTotsArxius.isFile()){
                System.out.println("No pot ser la ruta d'un fitxer:");
                rutaTotsArxius = cambiaRutaFile(lector, lleguirRuta);
            } else if (!rutaTotsArxius.exists()) {
                System.out.println("La ruta no existeix:");
                rutaTotsArxius = cambiaRutaFile(lector, lleguirRuta);
            }else {
                rutaCorrecte = true;
            }
        } while (!rutaCorrecte);
        return rutaTotsArxius;
    }

    private File cambiaRutaFile(Scanner lector, String lleguirRuta) {//Llegir una ruta i assigna-li a un objecte file
        lleguirRuta = lector.nextLine();
        File rutaCorrecta = new File(lleguirRuta);
        return rutaCorrecta;
    }

    private int validarInt(Scanner lector) {//Comprovar que els valors Integers siguin correctes
        boolean valorCorrecte = false;
        int numero = 0;
        do {
            valorCorrecte = lector.hasNextInt();
            if (valorCorrecte){
                numero = lector.nextInt();
            } else {
                System.out.println("Has d'introduir un número:");
            }
            lector.nextLine();
        }while (valorCorrecte == false);
        return numero;
    }
}