package Modele;

import java.io.*;
import java.util.Scanner;

public class Parametres {
    private int lignes;
    private int colonnes;
    private String prenom1, prenom2;
    private String nomFichier = "ressources/parametres.txt";

    public Parametres() throws IOException {
        lireFichierParametres();
    }

    public void lireFichierParametres() throws IOException {
        File f = new File(nomFichier);
        if (!f.exists()) {
            System.err.println("Le fichier '" + nomFichier + "' est introuvable.");
            return;
        }
        InputStream in = new FileInputStream(f);
        Scanner s = new Scanner(in);
        //lis une ligne
        if (s.hasNextLine()) {
            lignes = Math.max(3, Integer.parseInt(s.nextLine()));
            colonnes = Math.max(3, Integer.parseInt(s.nextLine()));
            prenom1 = s.nextLine();
            prenom2 = s.nextLine();
        }
        in.close();
        s.close();
    }

    public int getLignes() {
        return lignes;
    }

    public int getColonnes() {
        return colonnes;
    }

    public String getPrenom1() {
        return prenom1;
    }

    public String getPrenom2() {
        return prenom2;
    }

    public void sauvegarderParametres() throws IOException {
        File f = new File(nomFichier);
        f.createNewFile();
        OutputStream out = new FileOutputStream(f);
        out.write((lignes + System.lineSeparator()).getBytes());
        out.write((colonnes + System.lineSeparator()).getBytes());
        out.write((prenom1 + System.lineSeparator()).getBytes());
        out.write((prenom2 + System.lineSeparator()).getBytes());
        out.close();
    }

    public static void main(String[] args) throws IOException {
        Parametres parametres = new Parametres();
        System.out.println("Largeur: " + parametres.getLignes());
        System.out.println("Longueur: " + parametres.getColonnes());
        System.out.println("Prénom: " + parametres.getPrenom1());
        System.out.println("Prénom: " + parametres.getPrenom2());
    }
}
