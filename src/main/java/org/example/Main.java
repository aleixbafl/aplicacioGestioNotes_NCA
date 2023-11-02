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

        String usuari = loginUsuari(lector, secretari, rutaTotsArxius);

    }

    private String loginUsuari(Scanner lector, boolean secretari, File rutaTotsArxius) {
        String DNI = "", contrasenya = "";
        System.out.println("\nLogin");
        do {
            System.out.println("\nIntrodueix el teu DNI:");
            DNI = lleguirString(lector);
            while (!validarDNI(DNI)){
                System.out.println("El DNI es incorrecte:");
                DNI = lleguirString(lector);
            }

            System.out.println("\nIntrodueix la teva contrasenya:");
            contrasenya = lleguirString(lector);
        } while (!comprobarUsuari(secretari, DNI, contrasenya, rutaTotsArxius));

        return DNI;
    }

    private boolean comprobarUsuari(boolean secretari, String dni, String contrasenya, File rutaTotsArxius) {
        File arxiuUsuaris = new File(rutaTotsArxius.getPath() + "usuaris.txt");
        if (arxiuUsuaris.exists()){
            try {
                FileInputStream fis = new FileInputStream(arxiuUsuaris);
                ObjectInputStream ois = new ObjectInputStream(fis);
                boolean usuariExist = false;
                if (fis.available() == 0){
                    System.out.println("\nNo hi ha cap usuari guardat.");
                }
                while ((fis.available()>0) && !usuariExist){
                    loginUsuaris usuari = (loginUsuaris) ois.readObject();
                    if (usuari.getDni() == dni){
                        if (usuari.getContrasenya() == contrasenya){
                            System.out.println("\nUsuari correcte");
                            usuariExist = true;
                            return usuariExist;
                        } else {
                            System.out.println("\nLa contrasenya introduïda és incorrecta.");
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("\nL'arxiu dels usuaris no existeix.");
            File arxiuSecretari = new File(rutaTotsArxius.getPath() + "secretari.txt");
            if (arxiuSecretari.exists()){
                try {
                    FileInputStream fis = new FileInputStream(arxiuSecretari);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    while (fis.available() > 0){

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
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


    //INSERTAR SECRETARI
    private void InsertarSecretari(Scanner lector){
        System.out.println("Introdueix el DNI del secretari ");
        String DNISecretari = lleguirString(lector);
        System.out.println("Introdueix el nom del secretari");
        String nomSecretari = lleguirString(lector);
        System.out.println("Introdueix els cognoms del secretari");
        String cognomsSecretari = lleguirString(lector);
        System.out.println("Introdueix el seu numero de la SS");
        int numeroSSSecretari = validarInt(lector);

        secretari NouSecretari = new secretari(DNISecretari,nomSecretari,cognomsSecretari,numeroSSSecretari);

        System.out.println("Nou professor creat:\n" + NouSecretari.toString());
    }
    //INSERTAR ALUMNES
    private void InsertarAlumnes(Scanner lector){
        System.out.println("Introdueix el DNI de l'alumne");
        String DNI = lleguirString(lector);
        System.out.println("Introdueix el nom de l'alumne");
        String nomAlumne = lleguirString(lector);
        System.out.println("Introdueix els cognoms de l'alumne");
        String cognomsAlumne = lleguirString(lector);
        System.out.println("Introdueix l'expedient de l'alumne");
        String expedientAlumne = lleguirString(lector);
        System.out.println("Introdueix la matricula de l'alumne");
        String matriculaAlumne = lleguirString(lector);

        alumne NouAlumne = new alumne(DNI,nomAlumne,cognomsAlumne,expedientAlumne,matriculaAlumne);

        System.out.println("Nou alumne creat:\n" + NouAlumne.toString());
    }
    //INSERTAR ASSIGNATURA
    private void InsertarAssignatura(Scanner lector){
        System.out.print("Introdueix el nom de l'Assignatura: ");
        String nomAsignatura = lleguirString(lector);

        System.out.print("¿Está finalitzada? (true/false): ");
        boolean finalitzat = lector.nextBoolean();

        System.out.print("Introdueix la nota final: ");
        float notaFinal = lector.nextFloat();

        lector.nextLine();//Netejar

        System.out.print("Introdueix el tema: ");
        String tema = lleguirString(lector);

        System.out.print("Introdueix el número d'hores: ");
        int hores = validarInt(lector);

        asignatura novaAsignatura = new asignatura(nomAsignatura, finalitzat, notaFinal, nomAsignatura, tema, hores);

        System.out.println("Nova assignatura creada:\n" + novaAsignatura.toString());
    }
    //INSERTAR PROFESSOR
    private void InsertarProfessor(Scanner lector){
        System.out.println("Introdueix el DNI del professor");
        String DNIProfessor = lleguirString(lector);
        System.out.println("Introdueix el nom del professor");
        String nomProfessor = lleguirString(lector);
        System.out.println("Introdueix els cognoms del professor");
        String cognomsProfessor = lleguirString(lector);
        System.out.println("Introdueix el seu numero de la SS");
        int numeroSSProfessor = validarInt(lector);

        professo NouProfessor = new professo(DNIProfessor,nomProfessor,cognomsProfessor,numeroSSProfessor);

        System.out.println("Nou professor creat:\n" + NouProfessor.toString());
    }
    //OPCIONS PROFESSOR
    private void OpcionsProfessor(){
        System.out.println("Quina acció vols realitzar?");
        System.out.println("\n1- Insertar Notes");
        System.out.println("2- Modificar Notes");
        System.out.println("3- Eliminar Notes");
        System.out.println("4- Tancar ");
    }
    //OPCIONS SECRETARIA
    private void OpcionesSecretaria(){
        System.out.println("Quina acció vols realitzar?");
        //Professors
        System.out.println("\n1- Introduir nous Professors");
        System.out.println("2- Treure Professors ");
        //Secretaris
        System.out.println("\n3- Introduir Secretaris/es ");
        System.out.println("4- Treure Secretaris/es");
        //Alumnes
        System.out.println("\n5- Insertar nous Alumnes");
        System.out.println("6- Treure Alumnes");
        System.out.println("7- Veure notes dels Alumnes");
    }

}