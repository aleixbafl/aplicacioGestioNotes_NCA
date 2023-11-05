package org.example;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.ArrayList;
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
        if (!usuari.equals("")){
            if (secretari){
                OpcionesSecretaria(lector, rutaTotsArxius);
            } else {
                OpcionsProfessor(lector);
            }
        }
    }

    private String loginUsuari(Scanner lector, boolean secretari, File rutaTotsArxius) {
        String DNI = "", contrasenya = "";
        System.out.println("\nLogin");
        do {
            System.out.println("\nIntrodueix el teu DNI:");
            DNI = validarDNI(lector);

            System.out.println("\nIntrodueix la teva contrasenya:");
            contrasenya = lleguirString(lector);
        } while (!comprobarUsuari(secretari, DNI, contrasenya, rutaTotsArxius, lector));
        return DNI;
    }

    private boolean comprobarUsuari(boolean secretari, String dni, String contrasenya, File rutaTotsArxius, Scanner lector) {
        File arxiuUsuaris = new File(rutaTotsArxius.getPath() + "\\" + "usuaris.txt");
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
                    if (usuari.getDni().equals(dni)){
                        if (usuari.getContrasenya().equals(contrasenya)){
                            System.out.println("\nUsuari correcte");
                            usuariExist = true;
                            ois.close();
                            fis.close();
                            return usuariExist;
                        } else {
                            System.out.println("\nLa contrasenya introduïda és incorrecta.");
                        }
                    } else {
                        System.out.println("El DNI es incorrecte o no esta reguistrat per a iniciar sessia.");
                    }
                }
                ois.close();
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("\nL'arxiu dels usuaris no existeix.");
            File arxiuSecretari = new File(rutaTotsArxius.getPath() + "\\" + "secretari.txt");
            if (arxiuSecretari.exists()){
                try {
                    FileInputStream fis = new FileInputStream(arxiuSecretari);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    if (fis.available() == 0){
                        crearSecretari(lector, rutaTotsArxius);
                    }
                    FileOutputStream fos = new FileOutputStream(rutaTotsArxius.getPath() + "\\" + "usuaris.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    while (fis.available() > 0){
                        secretari secretaris = (secretari) ois.readObject();
                        System.out.println("\nIntrodueix una contrasenya per al/la secrtari/ària " + secretaris.getNom() + secretaris.getCognom() +":");
                        String contrEncrip = lleguirString(lector);
                        System.out.println(contrEncrip);
                        loginUsuaris usuari = new loginUsuaris(secretaris.getDNI(), contrEncrip, true);
                        oos.writeObject(usuari);
                    }
                    oos.close();
                    fos.close();
                    ois.close();
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                crearSecretari(lector, rutaTotsArxius);
                try {
                    FileInputStream fis = new FileInputStream(arxiuSecretari.getPath());
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    FileOutputStream fos = new FileOutputStream(rutaTotsArxius.getPath() + "\\" + "usuaris.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    while (fis.available() > 0){
                        secretari secretaris = (secretari) ois.readObject();
                        System.out.println("\nIntrodueix una contrasenya per al/la secrtari/ària " + secretaris.getNom() + " " + secretaris.getCognom() +":");
                        String contrEncrip = lleguirString(lector);
                        loginUsuaris usuari = new loginUsuaris(secretaris.getDNI(), contrEncrip, true);
                        System.out.println(usuari.getDni());
                        System.out.println(usuari.getContrasenya());
                        oos.writeObject(usuari);
                    }
                    oos.close();
                    fos.close();
                    ois.close();
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    private void crearSecretari(Scanner lector, File rutaTotsArxius) {
        try {
            File rutaSecretari = new File(rutaTotsArxius.getPath() + "\\" + "secretari.txt");
            if (rutaSecretari.exists()){
                ArrayList<secretari> secretarisAL = new ArrayList<>();
                FileInputStream fis = new FileInputStream(rutaSecretari.getPath());
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0){
                    secretari unSecretari = (secretari) ois.readObject();
                    secretarisAL.add(unSecretari);
                }
                ois.close();
                fis.close();
                System.out.println("\nQuants secretaris/es vols introduir?");
                int numSecret = validarInt(lector);
                while (numSecret < 0){
                    System.out.println("No pots introduir menys de 0 secretaris/es:");
                    numSecret = validarInt(lector);
                }
                for (int i = 0; i < numSecret; i++){
                    secretari unSecretari = obtenirCrearSecretari(lector);
                    secretarisAL.add(unSecretari);
                }
                FileOutputStream fos = new FileOutputStream(rutaSecretari.getPath());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (secretari unSecretari:secretarisAL){
                    oos.writeObject(unSecretari);
                }
                oos.close();
                fos.close();
            } else {
                FileOutputStream fos = new FileOutputStream(rutaSecretari.getPath());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                System.out.println("\nQuants secretaris/es vols introduir?");
                int numSecret = validarInt(lector);
                while (numSecret < 0){
                    System.out.println("No pots introduir menys de 0 secretaris/es:");
                    numSecret = validarInt(lector);
                }
                for (int i = 0; i < numSecret; i++){
                    secretari unSecretari = obtenirCrearSecretari(lector);
                    oos.writeObject(unSecretari);
                }
                oos.close();
                fos.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private secretari obtenirCrearSecretari(Scanner lector) {
        System.out.println("\nIntrodueix el DNI:");
        String dni = validarDNI(lector);
        System.out.println("\nNom:");
        String nom = lector.nextLine();
        System.out.println("\nCognom/s:");
        String cognom = lector.nextLine();
        System.out.println("\nSeguretat Social:");
        String seguretatSocial = lector.nextLine();
        secretari unSecretari = new secretari(dni, nom,cognom, seguretatSocial);
        return unSecretari;
    }

    private String validarDNI(Scanner lector) {
        boolean correcto = false;
        String dni = "";

        do {
            dni = lector.nextLine().trim().toUpperCase();

            if (dni.length() == 9) {
                String numeros = dni.substring(0, 8);
                char letra = dni.charAt(8);

                if (numeros.matches("[0-9]+")) {
                    int numDNI = Integer.parseInt(numeros);
                    String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
                    char letraCalculada = letrasValidas.charAt(numDNI % 23);

                    if (letra == letraCalculada) {
                        correcto = true;
                    } else {
                        System.out.println("La lletra del DNI no es valid.");
                    }
                } else {
                    System.out.println("Els primers 8 caracters no son números.");
                }
            } else {
                System.out.println("El DNI no te els 9 caracters necessaris.");
            }
        } while (!correcto);

        return dni;
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
        try{
            System.out.println("Introdueix el DNI del secretari ");
            String DNISecretari = lleguirString(lector);
            System.out.println("Introdueix el nom del secretari");
            String nomSecretari = lleguirString(lector);
            System.out.println("Introdueix els cognoms del secretari");
            String cognomsSecretari = lleguirString(lector);
            System.out.println("Introdueix el seu numero de la SS");
            String numeroSSSecretari = lector.nextLine();

            secretari NouSecretari = new secretari(DNISecretari,nomSecretari,cognomsSecretari,numeroSSSecretari);

            System.out.println("Introdueix la ruta i el nom del fitxer (ex: C:\\carpeta\\alumnes.txt):");
            String ruta = lleguirString(lector);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
            oos.writeObject(NouSecretari);
            oos.close();

            System.out.println("Dades del secretari guardades correctament.");

        } catch (IOException e){
            System.out.println("Error al escriure l'arxiu: " + e.getMessage());
        }
    }
    //INSERTAR ALUMNES
    private void InsertarAlumnes(Scanner lector){
        try {
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

            alumne NouAlumne = new alumne(DNI, nomAlumne, cognomsAlumne, expedientAlumne, matriculaAlumne);

            System.out.println("Introdueix la ruta i el nom del fitxer (ex: C:\\carpeta\\alumnes.dat):");
            String ruta = lleguirString(lector);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
            oos.writeObject(NouAlumne);
            oos.close();

            System.out.println("Dades de l'alumne guardades correctament.");

        } catch (IOException e) {
            System.out.println("Error al escriure l'arxiu: " + e.getMessage());
        }
    }

    //INSERTAR ASSIGNATURA
    private void InsertarAssignatura(Scanner lector){
        try {
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

            System.out.println("Introdueix la ruta i el nom del fitxer (ex: C:\\carpeta\\alumnes.dat):");
            String ruta = lleguirString(lector);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
            oos.writeObject(novaAsignatura);
            oos.close();

            System.out.println("Dades de la assignatura guardades correctament.");

        } catch (IOException e) {
            System.out.println("Error al escriure l'arxiu: " + e.getMessage());
        }
    }
    //INSERTAR PROFESSOR
    private void InsertarProfessor(Scanner lector){
        try{
            System.out.println("Introdueix el DNI del professor");
            String DNIProfessor = lleguirString(lector);
            System.out.println("Introdueix el nom del professor");
            String nomProfessor = lleguirString(lector);
            System.out.println("Introdueix els cognoms del professor");
            String cognomsProfessor = lleguirString(lector);
            System.out.println("Introdueix el seu numero de la SS");
            String numeroSSProfessor = lleguirString(lector);

            professo NouProfessor = new professo(DNIProfessor,nomProfessor,cognomsProfessor,numeroSSProfessor);

            System.out.println("Introdueix la ruta i el nom del fitxer (ex: C:\\carpeta\\alumnes.dat):");
            String ruta = lleguirString(lector);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
            oos.writeObject(NouProfessor);
            oos.close();

            System.out.println("Dades del professor guardades correctament.");
        } catch (IOException e) {
            System.out.println("Error al escriure l'arxiu: " + e.getMessage());
        }
    }
    //OPCIONS PROFESSOR
    private void OpcionsProfessor(Scanner lector){
        int opcio = 0;

        while(opcio!=4){
            System.out.println("Quina acció vols realitzar?");
            System.out.println("\n1- Insertar Notes");
            System.out.println("2- Modificar Notes");
            System.out.println("3- Eliminar Notes");
            System.out.println("4- Tancar ");

            opcio = validarInt(lector);

            if(opcio==1){
            }else if(opcio == 2){
            }else if(opcio == 3){
            }else if(opcio == 4){
                Notes();
            }
        }
    }
    //OPCIONS SECRETARIA
    private void OpcionesSecretaria(Scanner lector, File rutaTotsArxius){
        int opcio = 0;

        while(opcio!=8){
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
            System.out.println("8- Tancar");
            System.out.println("\nQuina acció vols realitzar?");

            do {
                opcio = validarInt(lector);
                if ((opcio < 1) || (opcio > 8)){
                    System.out.println("Has d'introduir un número de l'1 al 8:");
                }
            } while ((opcio < 1) || (opcio > 8));

            ArrayList<professo> professosAL = new ArrayList<>();
            ArrayList<secretari> secretarisAL = new ArrayList<>();
            ArrayList<alumne> alumnesAL = new ArrayList<>();
            ArrayList<impartir> impartirAL = new ArrayList<>();

            lleguirFitxersProfeSecrAlumImpa(rutaTotsArxius, professosAL,secretarisAL, alumnesAL, impartirAL);
            if(opcio==1){
                System.out.println("\nQuants professors vols introduir?");
                int numProfes = noNegatiu(lector);
                for (int i = 0; i < numProfes; i++){
                    System.out.println("\nDNI:");
                    String DNI = validarDNI(lector);
                    System.out.println("\nNom");
                    String nom = lleguirString(lector);
                    System.out.println("\nCognom");
                    String cognom = lleguirString(lector);
                    System.out.println("\nSeguretat Social");
                    String segurSocial = lleguirString(lector);
                    professo nouProfe = new professo(DNI, nom, cognom, segurSocial);
                    professosAL.add(nouProfe);
                }
            }else if(opcio == 2){
            }else if(opcio == 3){
            }else if(opcio == 4){
            }else if(opcio == 5){
            }else if(opcio == 6){
            }else if(opcio == 7){
            }else if(opcio == 8){
                Notes();
            }
        }
    }

    private int noNegatiu(Scanner lector) {
        int num = validarInt(lector);
        while (num < 0){
            System.out.println("No pot ser un número negatiu (0 per a sortir):");
            num = validarInt(lector);
        }
        return num;
    }

    private void lleguirFitxersProfeSecrAlumImpa(File rutaTotsArxius, ArrayList<professo> professosAL, ArrayList<secretari> secretarisAL, ArrayList<alumne> alumnesAL, ArrayList<impartir> impartirAL) {
        try {//Lleguir fitxer professors
            FileInputStream fisProfes = new FileInputStream(rutaTotsArxius+ "\\" + "professors.txt");
            ObjectInputStream oisProfes = new ObjectInputStream(fisProfes);
            while (fisProfes.available() > 0){
                professo prof = (professo) oisProfes.readObject();
                professosAL.add(prof);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Lleguir fitxer secretari
        try {
            FileInputStream fisProfes = new FileInputStream(rutaTotsArxius+ "\\" + "secretari.txt");
            ObjectInputStream oisProfes = new ObjectInputStream(fisProfes);
            while (fisProfes.available() > 0){
                secretari secre = (secretari) oisProfes.readObject();
                secretarisAL.add(secre);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Lleguir fitxer Alumne
        try {
            FileInputStream fisProfes = new FileInputStream(rutaTotsArxius+ "\\" + "alumnes.txt");
            ObjectInputStream oisProfes = new ObjectInputStream(fisProfes);
            while (fisProfes.available() > 0){
                alumne alum = (alumne) oisProfes.readObject();
                alumnesAL.add(alum);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Lleguir fitxer Alumne
        try {
            FileInputStream fisProfes = new FileInputStream(rutaTotsArxius+ "\\" + "impartir.txt");
            ObjectInputStream oisProfes = new ObjectInputStream(fisProfes);
            while (fisProfes.available() > 0){
                impartir impar = (impartir) oisProfes.readObject();
                impartirAL.add(impar);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}