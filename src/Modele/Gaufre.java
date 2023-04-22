package Modele;

import java.io.*;
import java.util.LinkedList;

public class Gaufre {
    protected int[][] cases;
    protected int lignes, colonnes;
    private int nb_cases_pleines;
    private int nb_cases;
    public Historique historique;

    public Gaufre(int i, int j) {
        cases = new int[i][j];
        lignes = i;
        colonnes = j;
        nb_cases = i * j;
        nb_cases_pleines = nb_cases-1;
        historique = new Historique();
    }

    public Historique getHistorique() {
        return historique;
    }

    public boolean estMangee(int i, int j) {
        return cases[i][j] == 1;
    }


    public void joue(Coup c) {
        ListeCoups lc = new ListeCoups();
        for (int i = c.getI(); i<= lignes-1; i++) {
            for (int j = c.getJ(); j <= colonnes - 1; j++) {
                if (cases[i][j] == 0) {
                    lc.ajoute(new Coup(i, j, 0, 1));
                    fixeValeurCase(1, i, j);
                    nb_cases_pleines--;
                }
            }
        }
        historique.ajoute(lc);
    }

    public double progression() {
        return ((double)nb_cases_pleines / ((double)nb_cases-1))*100;
    }

    public int colonnes() {
        return colonnes;
    }

    public int lignes() {
        return lignes;
    }

    public boolean peutAnnuler() {
        return historique.peutAnnuler();
    }

    public boolean peutRefaire() {
        return historique.peutRefaire();
    }

    public void annuler() {
        if(peutAnnuler()) {
            LinkedList<Coup> liste_coups = historique.annuler().getListe_coups();
            LinkedList<Coup> liste_coups_copie = new LinkedList<>();
            liste_coups_copie.addAll(liste_coups);
            while(!liste_coups_copie.isEmpty()){
                Coup h = liste_coups_copie.removeFirst();
                int i = h.getI();
                int j = h.getJ();
                int oldValeur = h.getOldValeur();
                fixeValeurCase(oldValeur, i, j);
                nb_cases_pleines++;
            }
        }
    }


    public void refaire() {
        if(peutRefaire()) {
            LinkedList<Coup> liste_coups = historique.refaire().getListe_coups();
            LinkedList<Coup> liste_coups_copie = new LinkedList<>();
            liste_coups_copie.addAll(liste_coups);
            while (!liste_coups_copie.isEmpty()) {
                Coup h = liste_coups_copie.removeFirst();
                int i = h.getI();
                int j = h.getJ();
                int oldValeur = h.getNewValeur();
                fixeValeurCase(oldValeur, i, j);
                nb_cases_pleines--;
            }
        }
    }

    public void fixeValeurCase(int v, int i, int j) {
        cases[i][j] = v;
    }

    public void sauvegarder() {
        try {
            File f = new File("sauvegarde.txt");
            OutputStream out = new FileOutputStream(f);
            out.write((lignes() + System.lineSeparator()).getBytes());
            //ajoute un saut de ligne avec System.lineSeparator()
            out.write((colonnes() + System.lineSeparator()).getBytes());
            String chaine = "";
            for (int i = 0; i <= lignes - 1; i++)
                for (int j = 0; j <= colonnes - 1; j++)
                    chaine += cases[i][j] + "|";
            out.write(chaine.getBytes());
            out.write(System.lineSeparator().getBytes());
            out.write(historique.toString().getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void charger() {
    }

    public void reinitialise() {
        for (int i = 0; i<= lignes -1; i++)
            for (int j = 0; j<= colonnes -1; j++)
                fixeValeurCase(0, i, j);
    }

    public void affiche(){
        for (int i = 0; i<= lignes -1; i++) {
            for (int j = 0; j<= colonnes -1; j++)
                System.out.print(cases[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
